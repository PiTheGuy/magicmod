package com.pitheguy.magicmod.container.itemhandlers;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExcludeItemsSlotItemHandler extends SlotItemHandler {
    final List<Item> invalidItems;

    public ExcludeItemsSlotItemHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition, List<Item> invalidItems) {
        super(itemHandler, index, xPosition, yPosition);
        this.invalidItems = invalidItems;
    }

    @Override
    public boolean isItemValid(@NotNull ItemStack stack) {
        return !invalidItems.contains(stack.getItem()) && super.isItemValid(stack);
    }
}
