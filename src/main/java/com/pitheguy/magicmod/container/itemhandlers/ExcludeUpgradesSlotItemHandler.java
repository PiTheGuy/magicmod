package com.pitheguy.magicmod.container.itemhandlers;

import com.pitheguy.magicmod.items.UpgradeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ExcludeUpgradesSlotItemHandler extends SlotItemHandler {
    public ExcludeUpgradesSlotItemHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        return !(stack.getItem() instanceof UpgradeItem) && super.mayPlace(stack);
    }
}
