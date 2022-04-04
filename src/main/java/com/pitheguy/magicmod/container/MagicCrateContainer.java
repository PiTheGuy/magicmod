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
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.Objects;

public class MagicCrateContainer extends Container {

    private MagicCrateTileEntity tileEntity;
    private IWorldPosCallable canInteractWithCallable;

    //Server constructor
    public MagicCrateContainer(final int windowID, final PlayerInventory playerInv, final MagicCrateTileEntity tile) {
        super(ModContainerTypes.MAGIC_CRATE.get(), windowID);
        this.tileEntity = tile;
        this.canInteractWithCallable = IWorldPosCallable.of(tile.getWorld(), tile.getPos());

        final int slotSizePlus2 = 18;
        final int startX = 8;
        final int invStartX = 26;
        //Magic Crate Inventory

        for (int row = 0; row  < 8; row++) {
            for (int col = 0; col < 11; col++) {
                int startY = 18;
                this.addSlot(new SlotItemHandler(tile.getInventory(), (row * 11) + col, startX + (col * slotSizePlus2), startY + (row * slotSizePlus2)));
            }
        }

        //Hotbar
        int hotbarY = 231;
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInv, col, (invStartX + (col * slotSizePlus2)), hotbarY));
        }
        //Main Player Inventory
        int invStartY = 173;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInv, 9+(row*9)+col, invStartX + (col * slotSizePlus2), invStartY + (row * slotSizePlus2)));
            }
        }


    }
    //Client constructor
    public MagicCrateContainer(final int windowID, final PlayerInventory playerInv, final PacketBuffer data) {
        this(windowID, playerInv, getTileEntity(playerInv, data));
    }

    private static MagicCrateTileEntity getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPos = playerInv.player.world.getTileEntity(data.readBlockPos());
        if (tileAtPos instanceof MagicCrateTileEntity) {
            return (MagicCrateTileEntity) tileAtPos;
        }
        throw new IllegalStateException("TileEntity is not correct " + tileAtPos);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, RegistryHandler.MAGIC_CRATE.get());
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(final PlayerEntity player, final int index) {
        ItemStack returnStack = ItemStack.EMPTY;
        final Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            final ItemStack slotStack = slot.getStack();
            returnStack = slotStack.copy();

            final int containerSlots = this.inventorySlots.size() - player.inventory.mainInventory.size();
            if (index < containerSlots) {
                if (!mergeItemStack(slotStack, containerSlots, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(slotStack, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }
            if (slotStack.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (slotStack.getCount() == returnStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, slotStack);
        }
        return returnStack;
    }
}
