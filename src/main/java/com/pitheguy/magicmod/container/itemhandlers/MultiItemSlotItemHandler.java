package com.pitheguy.magicmod.container.itemhandlers;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MultiItemSlotItemHandler extends SlotItemHandler {
    final List<Item> validItem;
    public MultiItemSlotItemHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition, List<Item> validItem) {
        super(itemHandler, index, xPosition, yPosition);
        this.validItem = validItem;
    }

    @Override
    public boolean isItemValid(@NotNull ItemStack stack) {
        return validItem.contains(stack.getItem()) && super.isItemValid(stack);
    }
}
