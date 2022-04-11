package com.pitheguy.magicmod.container.itemhandlers;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class SingleItemSlotItemHandler extends SlotItemHandler {
    final Item validItem;
    public SingleItemSlotItemHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition, Item validItem) {
        super(itemHandler, index, xPosition, yPosition);
        this.validItem = validItem;
    }

    @Override
    public boolean isItemValid(@NotNull ItemStack stack) {
        if (stack.getItem() == validItem) {
            return super.isItemValid(stack);
        } else {
            return false;
        }
    }
}