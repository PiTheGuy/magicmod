package com.pitheguy.magicmod.blocks.entity;

import com.google.common.annotations.VisibleForTesting;
import com.pitheguy.magicmod.MagicMod;
import com.pitheguy.magicmod.util.ModItemHandler;
import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public abstract class AutoActionBlockEntity extends BlockEntity implements MenuProvider {
    public static final List<Block> MINE_BLACKLIST = Arrays.asList(Blocks.LAVA, Blocks.WATER);
    protected final ModItemHandler inventory;
    public volatile Status status;
    @Nullable public MagicEnergizerBlockEntity fuelSourceTileEntity = null;
    public int mineCooldown = 60;
    public static final int BASE_TICKS_PER_MINE = 60;
    public final int baseRange;
    public final int rangeIncreaseWithUpgrade;
    public final int rangeIncreaseWithObsidianPlatedUpgrade;
    public int ticksPerMine = BASE_TICKS_PER_MINE;
    public int range;
    protected final int direction;
    protected final boolean invertedDirection;
    @Nullable protected Block filterBlock;

    public AutoActionBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state, int baseRange, int rangeIncreaseWithUpgrade, int rangeIncreaseWithObsidianPlatedUpgrade, MineableArea mineableArea) {
        super(tileEntityTypeIn, pos, state);
        this.baseRange = baseRange;
        this.range = this.baseRange;
        this.rangeIncreaseWithUpgrade = rangeIncreaseWithUpgrade;
        this.rangeIncreaseWithObsidianPlatedUpgrade = rangeIncreaseWithObsidianPlatedUpgrade;
        this.inventory = new ModItemHandler(38);
        this.direction = mineableArea.getDirection();
        this.invertedDirection = direction >= 0;
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        NonNullList<ItemStack> inv = NonNullList.withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compound, inv);
        this.inventory.setNonNullList(inv);
        this.mineCooldown = compound.getInt("mineCooldown");
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        ContainerHelper.saveAllItems(compound, this.inventory.toNonNullList());
        compound.putInt("mineCooldown", this.mineCooldown);
        compound.putString("status", this.getStatus()); //For debugging
    }

    public final IItemHandlerModifiable getInventory() {
        return this.inventory;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag nbt = new CompoundTag();
        this.saveAdditional(nbt);
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.serializeNBT();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        this.load(tag);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> this.inventory));
    }

    @Nullable
    public abstract AbstractContainerMenu createMenu(final int windowId, final Inventory playerInv, final Player playerIn);

    public void serverTick(Level level, BlockPos pos, BlockState state, AutoActionBlockEntity tile) {
        boolean dirty = false;
        if (this.status != Status.INVENTORY_FULL || this.hasInventorySpace()) {
            this.updateStatus();
            this.updateUpgrades();
            if (level != null && !level.isClientSide && this.status.isRunning() && this.mineCooldown <= 0) {
                BlockPos minePos = this.findMineableBlock();
                if (minePos != null) {
                    List<ItemStack> drops = Block.getDrops(level.getBlockState(minePos), (ServerLevel) level, minePos, level.getBlockEntity(minePos));
                    drops.stream().filter(drop -> !drop.isEmpty()).forEach(this::addItemToInventory);
                    level.destroyBlock(minePos, false);
                    this.mineCooldown = this.ticksPerMine;
                    dirty = true;
                }
            }
            if (this.status.isRunning() && this.mineCooldown > 0) this.mineCooldown--;
            if (dirty) this.setChanged();
        }
    }

    public Component getName() {
        return this.getDefaultName();
    }

    protected abstract Component getDefaultName();

    @Override
    public Component getDisplayName() {
        return this.getName();
    }

    public synchronized String getStatus() {
        return this.status == null ? "Unknown" : this.status.message;
    }

    protected void findFuelSource() {
        this.fuelSourceTileEntity = null;
        double minDistance = Double.MAX_VALUE;
        for (int x = -4; x <= 4; x++) {
            for (int y = -4; y <= 4; y++) {
                for (int z = -4; z <= 4; z++) {
                    double distance = Math.sqrt(x * x + y * y + z * z);
                    if (this.getLevel().getBlockState(this.worldPosition.offset(x, y, z)).getBlock() == RegistryHandler.MAGIC_ENERGIZER.get() && distance < minDistance) {
                        this.fuelSourceTileEntity = (MagicEnergizerBlockEntity) this.getLevel().getBlockEntity(this.worldPosition.offset(x, y, z));
                        minDistance = distance;
                    }
                }
            }
        }
    }

    protected void addItemToInventory(ItemStack itemStack) {
        if (!this.canAddItem(itemStack)) return;
        for (int i = 0; i < 36; i++) {
            itemStack = this.inventory.insertItem(i, itemStack, false);
            if (itemStack.isEmpty()) break;
        }
    }

    public synchronized void setStatus(Status status) {
        this.status = status;
    }

    public void updateStatus() {
        this.findFuelSource();
        if (this.fuelSourceTileEntity == null || this.fuelSourceTileEntity.fuel <= 0) {
            this.setStatus(Status.NOT_ENOUGH_FUEL);
            if (fuelSourceTileEntity != null) this.fuelSourceTileEntity.unregisterFuelConsumer(this);
        } else if (!this.hasInventorySpace()) {
            this.setStatus(Status.INVENTORY_FULL);
            this.fuelSourceTileEntity.unregisterFuelConsumer(this);
        } else if (this.findMineableBlock() == null) {
            this.setStatus(Status.FINISHED);
            this.fuelSourceTileEntity.unregisterFuelConsumer(this);
        } else {
            this.setStatus(Status.RUNNING);
            this.fuelSourceTileEntity.registerFuelConsumer(this);
        }
    }

    @Nullable private BlockPos findMineableBlock() {
        if (this.level == null) return null;
        for (int y = invertedDirection ? 0 : 1; y <= (invertedDirection ? this.level.getMaxBuildHeight() - this.worldPosition.getY() : this.worldPosition.getY() - this.level.getMinBuildHeight()); y++) {
            for (int x = -range; x <= range; x++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos pos = this.worldPosition.offset(x, y * direction, z);
                    BlockGetter reader = this.level.getChunkForCollisions(this.level.getChunkAt(pos).getPos().x, this.level.getChunkAt(pos).getPos().z);
                    BlockState state = this.level.getBlockState(pos);
                    //printNullReasons(pos, reader, state);

                    if (reader != null && !state.isAir() && state.getDestroySpeed(reader, pos) >= 0 && this.level.getBlockEntity(pos) == null && !MINE_BLACKLIST.contains(state.getBlock()) && this.blockMatchesFilter(state.getBlock()) && this.canMineBlock(state)) {
                        return pos;
                    }
                }
            }
        }
        return null;
    }

    @VisibleForTesting
    private void printNullReasons(BlockPos pos, BlockGetter reader, BlockState state) {
        if (state.getDestroySpeed(reader, pos) < 0) MagicMod.LOGGER.info("Block at {} is not breakable", pos);
        if (this.level.getBlockEntity(pos) != null) MagicMod.LOGGER.info("Tile entity found at {}", pos);
        if (MINE_BLACKLIST.contains(state.getBlock())) MagicMod.LOGGER.info("Block at {} is blacklisted", pos);
        if (!this.blockMatchesFilter(state.getBlock())) MagicMod.LOGGER.info("Block at {} doesn't match filter", pos);
        if (!this.canMineBlock(state)) MagicMod.LOGGER.info("Block at {} can't be mined by current auto-miner", pos);
    }

    protected abstract boolean canMineBlock(BlockState blockState);

    protected boolean blockMatchesFilter(Block block) {
        return this.filterBlock == null || block == this.filterBlock;
    }

    protected boolean hasInventorySpace() {
        return IntStream.range(0, 36).anyMatch(i -> this.inventory.getStackInSlot(i).isEmpty());
    }

    protected boolean canAddItem(ItemStack stack) {
        if (this.hasInventorySpace()) return true;
        int count = stack.getCount();
        for (int i = 0; i < 36; i++) {
            if (this.inventory.getStackInSlot(i).getItem() == stack.getItem()) {
                count -= 64 - this.inventory.getStackInSlot(i).getCount();
                if (count <= 0) return true;
            }
        }
        return false;
    }

    public void updateUpgrades() {
        int oldMineSpeed = this.ticksPerMine;
        this.ticksPerMine = BASE_TICKS_PER_MINE;
        this.range = baseRange;
        for (int i = 36; i <= 37; i++) {
            if (this.inventory.getStackInSlot(i).getItem() == RegistryHandler.SPEED_UPGRADE.get()) this.ticksPerMine /= 2.0;
            else if (this.inventory.getStackInSlot(i).getItem() == RegistryHandler.OBSIDIAN_PLATED_SPEED_UPGRADE.get()) this.ticksPerMine /= 3.8;
            else if (this.inventory.getStackInSlot(i).getItem() == RegistryHandler.RANGE_UPGRADE.get()) this.range += this.rangeIncreaseWithUpgrade;
            else if (this.inventory.getStackInSlot(i).getItem() == RegistryHandler.OBSIDIAN_PLATED_RANGE_UPGRADE.get()) this.range += this.rangeIncreaseWithObsidianPlatedUpgrade;
        }
        this.filterBlock = IntStream.rangeClosed(36, 37).mapToObj(this.inventory::getStackInSlot).filter(stack -> stack.getItem() == RegistryHandler.FILTER_UPGRADE.get() && stack.hasTag() && stack.getTag().contains("Filter")).findFirst().map(stack -> NbtUtils.readBlockState(stack.getTag().getCompound("Filter")).getBlock()).orElse(null);
        if (this.ticksPerMine != oldMineSpeed) {
            this.mineCooldown *= (double) this.ticksPerMine / oldMineSpeed;
        }
    }

    public enum Status {
        RUNNING("Running", true),
        FINISHED("Finished", false),
        NOT_ENOUGH_FUEL("Not Enough Fuel", false),
        INVENTORY_FULL("Inventory Full", false);

        final String message;
        final boolean running;
        Status(String message, boolean running) {
            this.message = message;
            this.running = running;
        }
        public String getMessage() {
            return this.message;
        }
        public boolean isRunning() {
            return this.running;
        }
    }

    public enum MineableArea {
        ABOVE(1),
        BELOW(-1);

        final int direction;

        MineableArea(int direction) {
            this.direction = direction;
        }

        public int getDirection() {
            return this.direction;
        }
    }
}
