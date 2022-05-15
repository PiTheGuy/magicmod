package com.pitheguy.magicmod.container.itemhandlers;

import com.pitheguy.magicmod.items.UpgradeItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ExcludeUpgradesSlotItemHandler extends SlotItemHandler {
    public ExcludeUpgradesSlotItemHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@NotNull ItemStack stack) {
        return !(stack.getItem() instanceof UpgradeItem) && super.isItemValid(stack);
    }
}
