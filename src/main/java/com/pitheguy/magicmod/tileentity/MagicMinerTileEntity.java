package com.pitheguy.magicmod.tileentity;

import com.pitheguy.magicmod.container.MagicMinerContainer;
import com.pitheguy.magicmod.init.ModTileEntityTypes;
import com.pitheguy.magicmod.util.ModItemHandler;
import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.IntStream;

public class MagicMinerTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    private final ModItemHandler inventory;
    public Status status = Status.NOT_ENOUGH_FUEL;
    @Nullable public MagicEnergizerTileEntity fuelSourceTileEntity = null;
    public int mineCooldown = 60;
    public static final int BASE_TICKS_PER_MINE = 60;
    public static final int BASE_RANGE = 5;
    public int ticksPerMine = BASE_TICKS_PER_MINE;
    public int range = BASE_RANGE;

    public MagicMinerTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        this.inventory = new ModItemHandler(38);
    }

    public MagicMinerTileEntity() {
        this(ModTileEntityTypes.MAGIC_MINER.get());
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        NonNullList<ItemStack> inv = NonNullList.withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, inv);
        this.inventory.setNonNullList(inv);
        this.mineCooldown = compound.getInt("mineCooldown");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        ItemStackHelper.saveAllItems(compound, this.inventory.toNonNullList());
        compound.putInt("mineCooldown", this.mineCooldown);
        return compound;
    }

    public final IItemHandlerModifiable getInventory() {
        return this.inventory;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return new SUpdateTileEntityPacket(this.pos, 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(CompoundNBT nbt) {
        this.read(nbt);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> this.inventory));
    }

    @Nullable
    @Override
    public Container createMenu(final int windowId, final PlayerInventory playerInv, final PlayerEntity playerIn) {
        return new MagicMinerContainer(windowId, playerInv, this);
    }

    @Override
    public void tick() {
        boolean dirty = false;
        if (this.status != Status.FINISHED) {
            if (this.status == Status.INVENTORY_FULL && this.hasInventorySpace()) {
                status = Status.RUNNING;
            }
            updateUpgrades();
            if (world != null && !world.isRemote() && this.status.isRunning() && mineCooldown <= 0) {
                BlockPos minePos = this.findMineableBlock();
                if (minePos != null) {
                    List<ItemStack> drops = Block.getDrops(world.getBlockState(minePos), (ServerWorld) world, minePos, world.getTileEntity(minePos));
                    for (ItemStack drop : drops) {
                        if (!addItemToInventory(drop)) {
                            status = Status.INVENTORY_FULL;
                            return;
                        }
                    }
                    world.destroyBlock(minePos, false);
                    mineCooldown = ticksPerMine;
                } else {
                    this.status = Status.FINISHED;
                    return;
                }
            }
            if (fuelSourceTileEntity != null) {
                fuelSourceTileEntity.unregisterFuelConsumer(this);
            }
            findFuelSource();
            if (fuelSourceTileEntity != null && fuelSourceTileEntity.fuel > 0) {
                if (status == Status.NOT_ENOUGH_FUEL) status = Status.RUNNING;
                if (this.status.isRunning()) fuelSourceTileEntity.registerFuelConsumer(this);
            } else status = Status.NOT_ENOUGH_FUEL;
            if (this.status.isRunning()) {
                dirty = true;
                if (mineCooldown > 0) mineCooldown--;
            }

            if (dirty) this.markDirty();
        }
    }

    public ITextComponent getName() {
        return this.getDefaultName();
    }

    private ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.magicmod.magic_miner");
    }

    @Override
    public ITextComponent getDisplayName() {
        return this.getName();
    }

    public Status getStatus() {
        return this.status;
    }

    private void findFuelSource() {
        this.fuelSourceTileEntity = null;
        double minDistance = Double.MAX_VALUE;
        for (int x = -4; x <= 4; x++) {
            for (int y = -4; y <= 4; y++) {
                for (int z = -4; z <= 4; z++) {
                    double distance = Math.sqrt(x * x + y * y + z * z);
                    if (this.getWorld().getBlockState(pos.add(x, y, z)).getBlock() == RegistryHandler.MAGIC_ENERGIZER.get() && distance < minDistance) {
                        this.fuelSourceTileEntity = (MagicEnergizerTileEntity) this.getWorld().getTileEntity(pos.add(x, y, z));
                        minDistance = distance;
                    }
                }
            }
        }
    }

    private boolean addItemToInventory(ItemStack itemStack) {
        if (!this.canPlaceItem(itemStack)) return false;
        for (int i = 0; i < 36; i++) {
            itemStack = this.inventory.insertItem(i, itemStack, false);
            if (itemStack.isEmpty()) break;
        }
        return itemStack.isEmpty();
    }

    @Nullable private BlockPos findMineableBlock() {
        if (world == null) return null;
        for (int y = 1; y <= this.pos.getY(); y++) {
            for (int x = -range; x <= range; x++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos pos = this.pos.add(x, -y, z);
                    IBlockReader reader = world.getBlockReader(world.getChunkAt(pos).getPos().x, world.getChunkAt(pos).getPos().z);
                    if (reader != null && !this.world.getBlockState(pos).isAir() && this.world.getBlockState(pos).getBlockHardness(reader, pos) >= 0) {
                        return pos;
                    }
                }
            }
        }
        return null;
    }

    private boolean hasInventorySpace() {
        return IntStream.range(0, 36).anyMatch(i -> this.inventory.getStackInSlot(i).isEmpty());
    }

    private boolean canPlaceItem(ItemStack stack) {
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
        this.range = BASE_RANGE;
        for (int i = 36; i <= 37; i++) {
            if (this.inventory.getStackInSlot(i).getItem() == RegistryHandler.SPEED_UPGRADE.get()) this.ticksPerMine /= 2.0;
            else if (this.inventory.getStackInSlot(i).getItem() == RegistryHandler.RANGE_UPGRADE.get()) this.range++;
        }
        if (this.ticksPerMine != oldMineSpeed) {
            this.mineCooldown *= (double) this.ticksPerMine / oldMineSpeed;
        }
    }

    public enum Status {
        RUNNING("Running", true),
        FINISHED("Finished", false),
        NOT_ENOUGH_FUEL("Not Enough Fuel", false),
        INVENTORY_FULL("Inventory Full", false);

        String message;
        boolean running;
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
}
