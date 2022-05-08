package com.pitheguy.magicmod.tileentity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.pitheguy.magicmod.container.MagicEnergizerContainer;
import com.pitheguy.magicmod.init.ModTileEntityTypes;
import com.pitheguy.magicmod.util.ModItemHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
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
import java.util.Map;

import static com.pitheguy.magicmod.util.RegistryHandler.*;

public class MagicEnergizerTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    private final ModItemHandler inventory;
    public int fuel = 0;
    public int fuelConsumptionPerTick = 0;
    public static final int MAX_FUEL = 2000;
    public static final Map<Item,Integer> ITEM_FUEL_AMOUNT = Maps.newHashMap(ImmutableMap.of(MAGIC_FUEL.get(), 100));
    public MagicEnergizerTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        this.inventory = new ModItemHandler(1);
    }

    public MagicEnergizerTileEntity() {
        this(ModTileEntityTypes.MAGIC_ENERGIZER.get());
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        NonNullList<ItemStack> inv = NonNullList.withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, inv);
        this.inventory.setNonNullList(inv);
        this.fuel = compound.getInt("Fuel");
        this.fuelConsumptionPerTick = compound.getInt("FuelConsumption");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        ItemStackHelper.saveAllItems(compound, this.inventory.toNonNullList());
        compound.putInt("Fuel", this.fuel);
        compound.putInt("FuelConsumption", this.fuelConsumptionPerTick);
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
        return new MagicEnergizerContainer(windowId, playerInv, this);
    }

    @Override
    public void tick() {
        boolean dirty = false;
        if (world != null && !world.isRemote()) {
            while(ITEM_FUEL_AMOUNT.get(this.inventory.getStackInSlot(0).getItem()) != null && fuel <= MAX_FUEL - ITEM_FUEL_AMOUNT.get(this.inventory.getStackInSlot(0).getItem())) {
                fuel += ITEM_FUEL_AMOUNT.get(this.inventory.getStackInSlot(0).getItem());
                this.inventory.decrStackSize(0, 1);
                dirty = true;
            }
        }
        fuel -= fuelConsumptionPerTick;
        if(dirty) this.markDirty();
    }

    public ITextComponent getName() {
        return this.getDefaultName();
    }

    private ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.magicmod.magic_press");
    }

    @Override
    public ITextComponent getDisplayName() {
        return this.getName();
    }

    public void addFuelConsumption(int fuelConsumption) {
        this.fuelConsumptionPerTick += fuelConsumption;
    }
}