package com.pitheguy.magicmod.container;

import com.pitheguy.magicmod.init.ModContainerTypes;
import com.pitheguy.magicmod.tileentity.MagicCrateTileEntity;
import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;

public class MagicCrateContainer extends Container {

    public final MagicCrateTileEntity tileEntity;
    private final IWorldPosCallable canInteractWithCallable;

    public MagicCrateContainer(final int windowId, final PlayerInventory playerInventory,
                                 final MagicCrateTileEntity tileEntity) {
        super(ModContainerTypes.MAGIC_CRATE.get(), windowId);
        this.tileEntity = tileEntity;
        this.canInteractWithCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());

        // Main Inventory
        int startX = 8;
        int startY = 18;
        int slotSizePlus2 = 18;
        for (int row = 0; row < 8; ++row) {
            for (int column = 0; column < 11; ++column) {
                this.addSlot(new Slot(tileEntity, (row * 11) + column, startX + (column * slotSizePlus2),
                        startY + (row * slotSizePlus2)));
            }
        }

        // Main Player Inventory
        int startPlayerInvY = 173;
        int startPlayerInvX = 26;
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, startPlayerInvX + (column * slotSizePlus2),
                        startPlayerInvY + (row * slotSizePlus2)));
            }
        }

        // Hotbar
        int hotbarY = 231;
        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInventory, column, startPlayerInvX + (column * slotSizePlus2), hotbarY));
        }
    }

    private static MagicCrateTileEntity getTileEntity(final PlayerInventory playerInventory,
                                                        final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPos = playerInventory.player.world.getTileEntity(data.readBlockPos());
        if (tileAtPos instanceof MagicCrateTileEntity) {
            return (MagicCrateTileEntity) tileAtPos;
        }
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }

    public MagicCrateContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, RegistryHandler.MAGIC_CRATE.get());
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < 88) {
                if (!this.mergeItemStack(itemstack1, 88, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, 88, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        World world = tileEntity.getWorld();
        BlockPos pos = tileEntity.getPos();
        world.playSound(null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_BARREL_CLOSE, SoundCategory.BLOCKS, 0.5f,
                world.rand.nextFloat() * 0.1f + 0.9f);
    }
}
