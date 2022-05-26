package com.pitheguy.magicmod.blockentity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.pitheguy.magicmod.container.MagicEnergizerContainer;
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
import java.util.ArrayList;
import java.util.Map;

import static com.pitheguy.magicmod.util.RegistryHandler.MAGIC_FUEL;

public class MagicEnergizerBlockEntity extends BlockEntity implements MenuProvider {
    private final ModItemHandler inventory;
    public int fuel = 0;
    public int fuelConsumptionPerTick = 0;
    public ArrayList<BlockEntity> fuelConsumers = new ArrayList<>();
    public static final int MAX_FUEL = 6000;
    public static final Map<Item,Integer> ITEM_FUEL_AMOUNT = Maps.newHashMap(ImmutableMap.of(MAGIC_FUEL.get(), 300));
    public MagicEnergizerBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
        this.inventory = new ModItemHandler(1);
    }

    public MagicEnergizerBlockEntity(BlockPos pos, BlockState state) {
        this(ModTileEntityTypes.MAGIC_ENERGIZER.get(), pos, state);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        NonNullList<ItemStack> inv = NonNullList.withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compound, inv);
        this.inventory.setNonNullList(inv);
        this.fuel = compound.getInt("Fuel");
        this.fuelConsumptionPerTick = compound.getInt("FuelConsumption");
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        super.save(compound);
        ContainerHelper.saveAllItems(compound, this.inventory.toNonNullList());
        compound.putInt("Fuel", this.fuel);
        compound.putInt("FuelConsumption", this.fuelConsumptionPerTick);
        return compound;
    }

    public final IItemHandlerModifiable getInventory() {
        return this.inventory;
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        CompoundTag nbt = new CompoundTag();
        this.save(nbt);
        return new ClientboundBlockEntityDataPacket(this.worldPosition, 0, nbt);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = new CompoundTag();
        this.save(nbt);
        return nbt;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> this.inventory));
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(final int windowId, final Inventory playerInv, final Player playerIn) {
        return new MagicEnergizerContainer(windowId, playerInv, this);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, MagicEnergizerBlockEntity tile) {
        boolean dirty = false;
        tile.fuelConsumptionPerTick = tile.fuelConsumers.size();
        if (level != null && !level.isClientSide()) {
            while(ITEM_FUEL_AMOUNT.get(tile.inventory.getStackInSlot(0).getItem()) != null && tile.fuel <= MAX_FUEL - ITEM_FUEL_AMOUNT.get(tile.inventory.getStackInSlot(0).getItem())) {
                tile.fuel += ITEM_FUEL_AMOUNT.get(tile.inventory.getStackInSlot(0).getItem());
                tile.inventory.decrStackSize(0, 1);
                dirty = true;
            }
        }
        tile.fuel -= tile.fuelConsumptionPerTick;
        if(dirty) tile.setChanged();
    }

    public Component getName() {
        return this.getDefaultName();
    }

    private Component getDefaultName() {
        return new TranslatableComponent("container.magicmod.magic_energizer");
    }

    @Override
    public Component getDisplayName() {
        return this.getName();
    }

    public void registerFuelConsumer(BlockEntity consumer) {
        if (!this.fuelConsumers.contains(consumer)) {
            this.fuelConsumers.add(consumer);
        }
    }

    public void unregisterFuelConsumer(BlockEntity consumer) {
        this.fuelConsumers.remove(consumer);
    }
}