package com.pitheguy.magicmod.container.itemhandlers;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class MultiItemSlotItemHandler extends SlotItemHandler {
    final List<Item> validItems;
    public MultiItemSlotItemHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition, List<Item> validItems) {
        super(itemHandler, index, xPosition, yPosition);
        this.validItems = validItems;
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        return validItems.contains(stack.getItem()) && super.mayPlace(stack);
    }
}
