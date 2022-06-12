package com.pitheguy.magicmod.container;

import com.pitheguy.magicmod.container.itemhandlers.SingleItemSlotItemHandler;
import com.pitheguy.magicmod.init.ModContainerTypes;
import com.pitheguy.magicmod.blocks.entity.MagicInfuserBlockEntity;
import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nonnull;
import java.util.Objects;

public class MagicInfuserContainer extends AbstractContainerMenu {

    private final ContainerLevelAccess canInteractWithCallable;

    //Server constructor
    public MagicInfuserContainer(final int windowID, final Inventory playerInv, final MagicInfuserBlockEntity tile) {
        super(ModContainerTypes.MAGIC_INFUSER.get(), windowID);
        this.canInteractWithCallable = ContainerLevelAccess.create(tile.getLevel(), tile.getBlockPos());

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
    public MagicInfuserContainer(final int windowID, final Inventory playerInv, final FriendlyByteBuf data) {
        this(windowID, playerInv, getTileEntity(playerInv, data));
    }

    private static MagicInfuserBlockEntity getTileEntity(final Inventory playerInv, final FriendlyByteBuf data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final BlockEntity tileAtPos = playerInv.player.level.getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof MagicInfuserBlockEntity) {
            return (MagicInfuserBlockEntity) tileAtPos;
        }
        throw new IllegalStateException("TileEntity is not correct " + tileAtPos);
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(canInteractWithCallable, playerIn, RegistryHandler.MAGIC_INFUSER.get());
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(final Player player, final int index) {
        ItemStack returnStack = ItemStack.EMPTY;
        final Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            final ItemStack slotStack = slot.getItem();
            returnStack = slotStack.copy();

            final int containerSlots = this.slots.size() - player.getInventory().items.size();
            if (index < containerSlots) {
                if (!moveItemStackTo(slotStack, containerSlots, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(slotStack, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }
            if (slotStack.getCount() == 0) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (slotStack.getCount() == returnStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, slotStack);
        }
        return returnStack;
    }
}
