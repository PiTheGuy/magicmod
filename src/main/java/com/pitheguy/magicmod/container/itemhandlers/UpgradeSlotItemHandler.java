package com.pitheguy.magicmod.container.itemhandlers;

import com.pitheguy.magicmod.items.UpgradeItem;
import com.pitheguy.magicmod.tileentity.AutoActionTileEntity;
import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.stream.IntStream;

public class UpgradeSlotItemHandler extends SlotItemHandler {
    private final AutoActionTileEntity tileEntity;
    public UpgradeSlotItemHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition, AutoActionTileEntity tileEntity) {
        super(itemHandler, index, xPosition, yPosition);
        this.tileEntity = tileEntity;
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        return super.mayPlace(stack) && stack.getItem() instanceof UpgradeItem && (stack.getItem() != RegistryHandler.FILTER_UPGRADE.get() || IntStream.range(0, this.tileEntity.getInventory().getSlots()).noneMatch(i -> this.tileEntity.getInventory().getStackInSlot(i).getItem() == RegistryHandler.FILTER_UPGRADE.get()));
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public int getMaxStackSize(@Nonnull ItemStack stack) {
        ItemStack maxAdd = stack.copy();
        int maxInput = 1;
        maxAdd.setCount(maxInput);

        IItemHandler handler = this.getItemHandler();
        ItemStack currentStack = handler.getStackInSlot(this.getSlotIndex());
        if (handler instanceof IItemHandlerModifiable) {
            IItemHandlerModifiable handlerModifiable = (IItemHandlerModifiable) handler;

            handlerModifiable.setStackInSlot(this.getSlotIndex(), ItemStack.EMPTY);

            ItemStack remainder = handlerModifiable.insertItem(this.getSlotIndex(), maxAdd, true);

            handlerModifiable.setStackInSlot(this.getSlotIndex(), currentStack);

            return maxInput - remainder.getCount();
        } else {
            ItemStack remainder = handler.insertItem(this.getSlotIndex(), maxAdd, true);

            int current = currentStack.getCount();
            int added = maxInput - remainder.getCount();
            return current + added;
        }
    }
}
