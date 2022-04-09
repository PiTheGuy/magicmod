package com.pitheguy.magicmod.tileentity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.pitheguy.magicmod.container.MagicPressContainer;
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

public class MagicPressTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
    private ITextComponent customName;
    private final ModItemHandler inventory;
    public int fuel = 0;
    public final int maxFuel = 20;
    public static final Map<Item,Integer> ITEM_FUEL_AMOUNT = Maps.newHashMap(ImmutableMap.of(MAGIC_GEM.get(), 1, MAGIC_BLOCK_ITEM.get(), 9));
    public static final Map<Item, Item> RECIPES = Maps.newHashMap(new ImmutableMap.Builder<Item, Item>()
            .put(REINFORCED_MAGIC_HELMET.get(),OBSIDIAN_PLATED_REINFORCED_MAGIC_HELMET.get())
            .put(REINFORCED_MAGIC_CHESTPLATE.get(),OBSIDIAN_PLATED_REINFORCED_MAGIC_CHESTPLATE.get())
            .put(REINFORCED_MAGIC_LEGGINGS.get(),OBSIDIAN_PLATED_REINFORCED_MAGIC_LEGGINGS.get())
            .put(REINFORCED_MAGIC_BOOTS.get(),OBSIDIAN_PLATED_REINFORCED_MAGIC_BOOTS.get())
            .put(REINFORCED_MAGIC_PICKAXE.get(),OBSIDIAN_PLATED_REINFORCED_MAGIC_PICKAXE.get())
            .put(REINFORCED_MAGIC_AXE.get(),OBSIDIAN_PLATED_REINFORCED_MAGIC_AXE.get())
            .put(REINFORCED_MAGIC_SHOVEL.get(),OBSIDIAN_PLATED_REINFORCED_MAGIC_SHOVEL.get())
            .put(REINFORCED_MAGIC_SWORD.get(),OBSIDIAN_PLATED_REINFORCED_MAGIC_SWORD.get())
            .put(REINFORCED_MAGIC_HOE.get(),OBSIDIAN_PLATED_REINFORCED_MAGIC_HOE.get())
            .build());
    public MagicPressTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        this.inventory = new ModItemHandler(10);
    }

    public MagicPressTileEntity() {
        this(ModTileEntityTypes.MAGIC_PRESS.get());
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        NonNullList<ItemStack> inv = NonNullList.withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, inv);
        this.inventory.setNonNullList(inv);
        this.fuel = compound.getInt("Fuel");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        ItemStackHelper.saveAllItems(compound, this.inventory.toNonNullList());
        compound.putInt("Fuel", this.fuel);
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
        return new MagicPressContainer(windowId, playerInv, this);
    }

    @Override
    public void tick() {
        boolean dirty = false;
        if (world != null && !world.isRemote()) {
            while(ITEM_FUEL_AMOUNT.get(this.inventory.getStackInSlot(3).getItem()) != null && fuel <= maxFuel - ITEM_FUEL_AMOUNT.get(this.inventory.getStackInSlot(3).getItem())) {
                fuel += ITEM_FUEL_AMOUNT.get(this.inventory.getStackInSlot(3).getItem());
                this.inventory.decrStackSize(3, 1);
                dirty = true;
            }
            if (RECIPES.get(this.inventory.getStackInSlot(0).getItem()) != null && this.inventory.getStackInSlot(1).getItem() == OBSIDIAN_PLATE.get() && this.inventory.getStackInSlot(1).getCount() >= 32 && fuel >= 1) {
                fuel--;
                this.inventory.insertItem(2,new ItemStack(RECIPES.get(this.inventory.getStackInSlot(0).getItem()), 1), false);
                this.inventory.decrStackSize(0,1);
                this.inventory.decrStackSize(1,32);
                dirty = true;
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
        return new TranslationTextComponent("container.magicmod.magic_press");
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