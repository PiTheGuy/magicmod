package com.pitheguy.magicmod.container;

import com.pitheguy.magicmod.container.itemhandlers.ExcludeItemsSlotItemHandler;
import com.pitheguy.magicmod.container.itemhandlers.UpgradeSlotItemHandler;
import com.pitheguy.magicmod.init.ModContainerTypes;
import com.pitheguy.magicmod.tileentity.MagicMinerTileEntity;
import com.pitheguy.magicmod.util.FunctionalIntReferenceHolder;
import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Objects;

public class MagicMinerContainer extends Container {

    public MagicMinerTileEntity tileEntity;
    private final IWorldPosCallable canInteractWithCallable;
    public FunctionalIntReferenceHolder mineCooldown;

    //Server constructor
    public MagicMinerContainer(final int windowID, final PlayerInventory playerInv, final MagicMinerTileEntity tile) {
        super(ModContainerTypes.MAGIC_MINER.get(), windowID);

        //Upgrade Slots
        this.addSlot(new UpgradeSlotItemHandler(tile.getInventory(), 36, 177, 26));
        this.addSlot(new UpgradeSlotItemHandler(tile.getInventory(), 37, 177, 44));
        this.canInteractWithCallable = IWorldPosCallable.of(tile.getWorld(), tile.getPos());
        this.tileEntity = tile;
        final int slotSizePlus2 = 18;
        final int startX = 8;
        //Magic Miner Inventory
        int startY = 18;
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 9; column++) {
                this.addSlot(new ExcludeItemsSlotItemHandler(tile.getInventory(), (row * 9) + column, startX + (column * slotSizePlus2), startY + (row * slotSizePlus2), Arrays.asList(RegistryHandler.SPEED_UPGRADE.get(), RegistryHandler.RANGE_UPGRADE.get())));
            }
        }

        //Hotbar
        int hotbarY = 162;
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInv, col, (startX + (col * slotSizePlus2)), hotbarY));
        }
        //Main Player Inventory
        int invStartY = 105;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInv, 9+(row*9)+col, startX + (col * slotSizePlus2), invStartY + (row * slotSizePlus2)));
            }
        }
        this.trackInt(mineCooldown = new FunctionalIntReferenceHolder(() -> this.tileEntity.mineCooldown,
                value -> this.tileEntity.mineCooldown = value));
    }
    //Client constructor
    public MagicMinerContainer(final int windowID, final PlayerInventory playerInv, final PacketBuffer data) {
        this(windowID, playerInv, getTileEntity(playerInv, data));
    }

    private static MagicMinerTileEntity getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPos = playerInv.player.world.getTileEntity(data.readBlockPos());
        if (tileAtPos instanceof MagicMinerTileEntity) {
            return (MagicMinerTileEntity) tileAtPos;
        }
        throw new IllegalStateException("TileEntity is not correct " + tileAtPos);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, RegistryHandler.MAGIC_MINER.get());
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

    public int getMineCooldownScaled() {
        return this.mineCooldown.get() != 0 && this.tileEntity.mineCooldown != 0 ? 52 - this.mineCooldown.get() * 52 / this.tileEntity.ticksPerMine : 0;
    }
}
