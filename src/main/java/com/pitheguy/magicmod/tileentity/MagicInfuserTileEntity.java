package com.pitheguy.magicmod.tileentity;

import com.pitheguy.magicmod.container.MagicInfuserContainer;
import com.pitheguy.magicmod.init.ModTileEntityTypes;
import com.pitheguy.magicmod.util.ModItemHandler;
import com.pitheguy.magicmod.util.RegistryHandler;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;

public class MagicInfuserTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    private ITextComponent customName;
    private final ModItemHandler inventory;

    public MagicInfuserTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        this.inventory = new ModItemHandler(10);
    }

    public MagicInfuserTileEntity() {
        this(ModTileEntityTypes.MAGIC_INFUSER.get());
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        NonNullList<ItemStack> inv = NonNullList.withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, inv);
        this.inventory.setNonNullList(inv);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        ItemStackHelper.saveAllItems(compound, this.inventory.toNonNullList());
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
        return new MagicInfuserContainer(windowId, playerInv, this);
    }

    @Override
    public void tick() {
        boolean dirty = false;
        if (world != null && !world.isRemote()) {
            while(this.inventory.getStackInSlot(0).getItem() == RegistryHandler.MAGIC_ORB_RED.get() &&
                    this.inventory.getStackInSlot(1).getItem() == RegistryHandler.MAGIC_ORB_ORANGE.get() &&
                    this.inventory.getStackInSlot(2).getItem() == RegistryHandler.MAGIC_ORB_YELLOW.get() &&
                    this.inventory.getStackInSlot(3).getItem() == RegistryHandler.MAGIC_ORB_GREEN.get() &&
                    this.inventory.getStackInSlot(4).getItem() == RegistryHandler.MAGIC_ORB_BLUE.get() &&
                    this.inventory.getStackInSlot(5).getItem() == RegistryHandler.MAGIC_ORB_PURPLE.get() &&
                    this.inventory.getStackInSlot(6).getItem() == RegistryHandler.MAGIC_ORB_MAGENTA.get() &&
                    this.inventory.getStackInSlot(7).getItem() == RegistryHandler.MAGIC_ORB_BLACK.get() &&
                    this.inventory.getStackInSlot(8).getItem() == RegistryHandler.MAGIC_ORB_WHITE.get() &&
                    this.inventory.getStackInSlot(9).getCount() < 63) {
                this.inventory.insertItem(9, new ItemStack(RegistryHandler.MAGIC_CORE.get(), 2), false);
                for (int i = 0; i < 9; i++) {
                    this.inventory.decrStackSize(i, 1);
                    dirty = true;
                }
            }
        }
        if(dirty) this.markDirty();
    }
    public void setCustomName(ITextComponent name) {
        this.customName = name;
    }

    public ITextComponent getName() {
        return this.customName != null ? this.customName : this.getDefaultName();
    }

    private ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.magicmod.magic_infuser");
    }

    @Override
    public ITextComponent getDisplayName() {
        return this.getName();
    }

    @Nullable
    public ITextComponent getCustomName() {
        return this.customName;
    }
}