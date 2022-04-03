package com.pitheguy.magicmod.container;

import com.pitheguy.magicmod.container.itemhandlers.magicinfuser.SingleItemSlotItemHandler;
import com.pitheguy.magicmod.init.ModContainerTypes;
import com.pitheguy.magicmod.tileentity.MagicInfuserTileEntity;
import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

import javax.annotation.Nonnull;
import java.util.Objects;

public class MagicInfuserContainer extends Container {

    private MagicInfuserTileEntity tileEntity;
    private IWorldPosCallable canInteractWithCallable;

    //Server constructor
    public MagicInfuserContainer(final int windowID, final PlayerInventory playerInv, final MagicInfuserTileEntity tile) {
        super(ModContainerTypes.MAGIC_INFUSER.get(), windowID);
        this.tileEntity = tile;
        this.canInteractWithCallable = IWorldPosCallable.of(tile.getWorld(), tile.getPos());

        final int slotSizePlus2 = 18;
        final int startX = 8;
        //Hotbar
        int hotbarY = 190;
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInv, col, (startX + (col * slotSizePlus2)), hotbarY));
        }
        //Main Player Inventory
        int startY = 132;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInv, 9+(row*9)+col, startX + (col * slotSizePlus2), startY + (row * slotSizePlus2)));
            }
        }
        //Magic Infuser Inventory
        final int[][] inputCoords = {{79,8},{113,21},{130,51},{124,86},{97,108},{63,108},{36,86},{30,51},{47,21}};
        final Item[] slotItems = {RegistryHandler.MAGIC_ORB_RED.get(), RegistryHandler.MAGIC_ORB_ORANGE.get(), RegistryHandler.MAGIC_ORB_YELLOW.get(), RegistryHandler.MAGIC_ORB_GREEN.get(), RegistryHandler.MAGIC_ORB_BLUE.get(), RegistryHandler.MAGIC_ORB_PURPLE.get(), RegistryHandler.MAGIC_ORB_MAGENTA.get(), RegistryHandler.MAGIC_ORB_BLACK.get(), RegistryHandler.MAGIC_ORB_WHITE.get()};
        for (int i = 0; i < 9; i++) {
            this.addSlot(new SingleItemSlotItemHandler(tile.getInventory(), i, inputCoords[i][0], inputCoords[i][1], slotItems[i]));
        }
        this.addSlot(new SingleItemSlotItemHandler(tile.getInventory(), 9, 80, 58, RegistryHandler.MAGIC_CORE.get()));


    }
    //Client constructor
    public MagicInfuserContainer(final int windowID, final PlayerInventory playerInv, final PacketBuffer data) {
        this(windowID, playerInv, getTileEntity(playerInv, data));
    }

    private static MagicInfuserTileEntity getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPos = playerInv.player.world.getTileEntity(data.readBlockPos());
        if (tileAtPos instanceof MagicInfuserTileEntity) {
            return (MagicInfuserTileEntity) tileAtPos;
        }
        throw new IllegalStateException("TileEntity is not correct " + tileAtPos);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, RegistryHandler.MAGIC_INFUSER.get());
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
