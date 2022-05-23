package com.pitheguy.magicmod.tileentity;

import com.google.common.annotations.VisibleForTesting;
import com.pitheguy.magicmod.MagicMod;
import com.pitheguy.magicmod.util.ModItemHandler;
import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public abstract class AutoActionTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    public static final List<Block> MINE_BLACKLIST = Arrays.asList(Blocks.LAVA, Blocks.WATER);
    protected final ModItemHandler inventory;
    public volatile Status status;
    @Nullable public MagicEnergizerTileEntity fuelSourceTileEntity = null;
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

    public AutoActionTileEntity(TileEntityType<?> tileEntityTypeIn, int baseRange, int rangeIncreaseWithUpgrade, int rangeIncreaseWithObsidianPlatedUpgrade, MineableArea mineableArea) {
        super(tileEntityTypeIn);
        this.baseRange = baseRange;
        this.range = this.baseRange;
        this.rangeIncreaseWithUpgrade = rangeIncreaseWithUpgrade;
        this.rangeIncreaseWithObsidianPlatedUpgrade = rangeIncreaseWithObsidianPlatedUpgrade;
        this.inventory = new ModItemHandler(38);
        this.direction = mineableArea.getDirection();
        this.invertedDirection = direction >= 0;
    }

    @Override
    public void load(BlockState state, CompoundNBT compound) {
        super.load(state, compound);
        NonNullList<ItemStack> inv = NonNullList.withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, inv);
        this.inventory.setNonNullList(inv);
        this.mineCooldown = compound.getInt("mineCooldown");
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        ItemStackHelper.saveAllItems(compound, this.inventory.toNonNullList());
        compound.putInt("mineCooldown", this.mineCooldown);
        compound.putString("status", this.getStatus()); //For debugging
        return compound;
    }

    public final IItemHandlerModifiable getInventory() {
        return this.inventory;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        this.save(nbt);
        return new SUpdateTileEntityPacket(this.worldPosition, 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.load(this.level.getBlockState(pkt.getPos()), pkt.getTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = new CompoundNBT();
        this.save(nbt);
        return nbt;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> this.inventory));
    }

    @Nullable
    @Override
    public abstract Container createMenu(final int windowId, final PlayerInventory playerInv, final PlayerEntity playerIn);
    
    @Override
    public void tick() {
        boolean dirty = false;
        if (this.status != Status.INVENTORY_FULL || this.hasInventorySpace()) {
            this.updateStatus();
            this.updateUpgrades();
            if (level != null && !level.isClientSide && this.status.isRunning() && mineCooldown <= 0) {
                BlockPos minePos = this.findMineableBlock();
                if (minePos != null) {
                    List<ItemStack> drops = Block.getDrops(level.getBlockState(minePos), (ServerWorld) level, minePos, level.getBlockEntity(minePos));
                    drops.stream().filter(drop -> !drop.isEmpty()).forEach(this::addItemToInventory);
                    level.destroyBlock(minePos, false);
                    mineCooldown = ticksPerMine;
                    dirty = true;
                }
            }
            if (this.status.isRunning() && mineCooldown > 0) mineCooldown--;
            if (dirty) this.setChanged();
        }
    }

    public ITextComponent getName() {
        return this.getDefaultName();
    }

    protected abstract ITextComponent getDefaultName();

    @Override
    public ITextComponent getDisplayName() {
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
                    if (this.getLevel().getBlockState(new BlockPos(Vector3d.atCenterOf(this.worldPosition).add(x, y, z))).getBlock() == RegistryHandler.MAGIC_ENERGIZER.get() && distance < minDistance) {
                        this.fuelSourceTileEntity = (MagicEnergizerTileEntity) this.getLevel().getBlockEntity(new BlockPos(Vector3d.atCenterOf(this.worldPosition).add(x, y, z)));
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
        for (int y = invertedDirection ? 0 : 1; y <= (invertedDirection ? this.level.getMaxBuildHeight() - this.worldPosition.getY() : this.worldPosition.getY()); y++) {
            for (int x = -range; x <= range; x++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos pos = this.worldPosition.offset(x, y * direction, z);
                    IBlockReader reader = this.level.getChunkForCollisions(this.level.getChunkAt(pos).getPos().x, this.level.getChunkAt(pos).getPos().z);
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
    private void printNullReasons(BlockPos pos, IBlockReader reader, BlockState state) {
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
        this.filterBlock = IntStream.rangeClosed(36, 37).mapToObj(this.inventory::getStackInSlot).filter(stack -> stack.getItem() == RegistryHandler.FILTER_UPGRADE.get() && stack.hasTag() && stack.getTag().contains("Filter")).findFirst().map(stack -> NBTUtil.readBlockState(stack.getTag().getCompound("Filter")).getBlock()).orElse(null);
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
