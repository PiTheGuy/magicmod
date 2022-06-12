package com.pitheguy.magicmod.container;

import com.pitheguy.magicmod.container.itemhandlers.ExcludeUpgradesSlotItemHandler;
import com.pitheguy.magicmod.container.itemhandlers.UpgradeSlotItemHandler;
import com.pitheguy.magicmod.blocks.entity.AutoActionBlockEntity;
import com.pitheguy.magicmod.util.FunctionalIntDataSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public abstract class AutoActionContainer<T extends AutoActionBlockEntity> extends AbstractContainerMenu {

    public T tileEntity;
    protected final ContainerLevelAccess canInteractWithCallable;
    public FunctionalIntDataSlot mineCooldown;

    //Server constructor
    public AutoActionContainer(final int windowID, final Inventory playerInv, final T tile, MenuType<?> containerType) {
        super(containerType, windowID);
        this.tileEntity = tile;

        //Upgrade Slots
        this.addSlot(new UpgradeSlotItemHandler(tile.getInventory(), 36, 177, 26, this.tileEntity));
        this.addSlot(new UpgradeSlotItemHandler(tile.getInventory(), 37, 177, 44, this.tileEntity));
        this.canInteractWithCallable = ContainerLevelAccess.create(tile.getLevel(), tile.getBlockPos());
        final int slotSizePlus2 = 18;
        final int startX = 8;
        //Inventory
        int startY = 18;
        for (int row = 0; row < 4; row++) {
            for (int column = 0; column < 9; column++) {
                this.addSlot(new ExcludeUpgradesSlotItemHandler(tile.getInventory(), (row * 9) + column, startX + (column * slotSizePlus2), startY + (row * slotSizePlus2)));
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
        this.addDataSlot(mineCooldown = new FunctionalIntDataSlot(() -> this.tileEntity.mineCooldown,
                value -> this.tileEntity.mineCooldown = value));
    }

    @Override
    public abstract boolean stillValid(Player playerIn);

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

    public int getMineCooldownScaled() {
        return this.mineCooldown.get() != 0 && this.tileEntity.mineCooldown != 0 ? 52 - this.mineCooldown.get() * 52 / this.tileEntity.ticksPerMine : 0;
    }
}
