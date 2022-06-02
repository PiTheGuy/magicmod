package com.pitheguy.magicmod.blockentity;

import com.pitheguy.magicmod.container.MagicInfuserContainer;
import com.pitheguy.magicmod.init.ModTileEntityTypes;
import com.pitheguy.magicmod.util.ModItemHandler;
import com.pitheguy.magicmod.util.RegistryHandler;
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

public class MagicInfuserBlockEntity extends BlockEntity implements MenuProvider {
    private final ModItemHandler inventory;

    public MagicInfuserBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
        this.inventory = new ModItemHandler(10);
    }

    public MagicInfuserBlockEntity(BlockPos pos, BlockState state) {
        this(ModTileEntityTypes.MAGIC_INFUSER.get(), pos, state);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        NonNullList<ItemStack> inv = NonNullList.withSize(this.inventory.getSlots(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compound, inv);
        this.inventory.setNonNullList(inv);
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        ContainerHelper.saveAllItems(compound, this.inventory.toNonNullList());
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
        return new MagicInfuserContainer(windowId, playerInv, this);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, MagicInfuserBlockEntity tile) {
        boolean dirty = false;
        if (level != null && !level.isClientSide) {
            while(tile.inventory.getStackInSlot(0).getItem() == RegistryHandler.MAGIC_ORB_RED.get() &&
                    tile.inventory.getStackInSlot(1).getItem() == RegistryHandler.MAGIC_ORB_ORANGE.get() &&
                    tile.inventory.getStackInSlot(2).getItem() == RegistryHandler.MAGIC_ORB_YELLOW.get() &&
                    tile.inventory.getStackInSlot(3).getItem() == RegistryHandler.MAGIC_ORB_GREEN.get() &&
                    tile.inventory.getStackInSlot(4).getItem() == RegistryHandler.MAGIC_ORB_BLUE.get() &&
                    tile.inventory.getStackInSlot(5).getItem() == RegistryHandler.MAGIC_ORB_PURPLE.get() &&
                    tile.inventory.getStackInSlot(6).getItem() == RegistryHandler.MAGIC_ORB_MAGENTA.get() &&
                    tile.inventory.getStackInSlot(7).getItem() == RegistryHandler.MAGIC_ORB_BLACK.get() &&
                    tile.inventory.getStackInSlot(8).getItem() == RegistryHandler.MAGIC_ORB_WHITE.get() &&
                    tile.inventory.getStackInSlot(9).getCount() < 63) {
                tile.inventory.insertItem(9, new ItemStack(RegistryHandler.MAGIC_CORE.get(), 2), false);
                for (int i = 0; i < 9; i++) {
                    tile.inventory.decrStackSize(i, 1);
                    dirty = true;
                }
            }
        }
        if(dirty) tile.setChanged();
    }

    public Component getName() {
        return this.getDefaultName();
    }

    private Component getDefaultName() {
        return new TranslatableComponent("container.magicmod.magic_infuser");
    }

    @Override
    public Component getDisplayName() {
        return this.getName();
    }

}