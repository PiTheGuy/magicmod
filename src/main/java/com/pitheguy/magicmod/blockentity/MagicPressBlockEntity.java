package com.pitheguy.magicmod.blockentity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.pitheguy.magicmod.container.MagicPressContainer;
import com.pitheguy.magicmod.init.ModTileEntityTypes;
import com.pitheguy.magicmod.util.ModItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;
import java.util.Map;

import static com.pitheguy.magicmod.util.RegistryHandler.*;

public class MagicPressBlockEntity extends BlockEntity implements MenuProvider {
    private final ModItemHandler inventory;
    public int fuel = 0;
    public final int maxFuel = 900;
    public final int fuelPerOperation = 27;
    public static final Map<Item,Integer> ITEM_FUEL_AMOUNT = Maps.newHashMap(ImmutableMap.of(MAGIC_NUGGET.get(), 1, MAGIC_GEM.get(), 9, MAGIC_BLOCK_ITEM.get(), 36));
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
    public MagicPressBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
        this.inventory = new ModItemHandler(10);
    }

    public MagicPressBlockEntity(BlockPos pos, BlockState state) {
        this(ModTileEntityTypes.MAGIC_PRESS.get(), pos, state);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        NonNullList<ItemStack> inv = NonNullList.withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compound, inv);
        this.inventory.setNonNullList(inv);
        this.fuel = compound.getInt("Fuel");
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        ContainerHelper.saveAllItems(compound, this.inventory.toNonNullList());
        compound.putInt("Fuel", this.fuel);
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
        CompoundTag nbt = new CompoundTag();
        this.saveAdditional(nbt);
        return nbt;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> this.inventory));
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(final int windowId, final Inventory playerInv, final Player playerIn) {
        return new MagicPressContainer(windowId, playerInv, this);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, MagicPressBlockEntity tile) {
        boolean dirty = false;
        if (level != null && !level.isClientSide) {
            while(ITEM_FUEL_AMOUNT.get(tile.inventory.getStackInSlot(3).getItem()) != null && tile.fuel <= tile.maxFuel - ITEM_FUEL_AMOUNT.get(tile.inventory.getStackInSlot(3).getItem())) {
                tile.fuel += ITEM_FUEL_AMOUNT.get(tile.inventory.getStackInSlot(3).getItem());
                tile.inventory.decrStackSize(3, 1);
                dirty = true;
            }
            if (RECIPES.get(tile.inventory.getStackInSlot(0).getItem()) != null && tile.inventory.getStackInSlot(1).getItem() == OBSIDIAN_PLATE.get() && tile.inventory.getStackInSlot(1).getCount() >= 32 && tile.fuel >= tile.fuelPerOperation) {
                tile.fuel -= tile.fuelPerOperation;
                tile.inventory.insertItem(2,new ItemStack(RECIPES.get(tile.inventory.getStackInSlot(0).getItem()), 1), false);
                tile.inventory.decrStackSize(0,1);
                tile.inventory.decrStackSize(1,32);
                dirty = true;
            }
        }
        if(dirty) tile.setChanged();
    }

    public Component getName() {
        return this.getDefaultName();
    }

    private Component getDefaultName() {
        return new TranslatableComponent("container.magicmod.magic_press");
    }

    @Override
    public Component getDisplayName() {
        return this.getName();
    }

}