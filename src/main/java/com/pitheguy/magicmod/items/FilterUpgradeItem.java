package com.pitheguy.magicmod.items;

import com.pitheguy.magicmod.MagicMod;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class FilterUpgradeItem extends UpgradeItem {
    public FilterUpgradeItem() {
        super(new Item.Properties().group(MagicMod.TAB).maxStackSize(1));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (context.getPlayer().isSneaking()) {
            ItemStack itemStack = context.getItem();
            CompoundNBT currentData = itemStack.hasTag() ? itemStack.getTag() : new CompoundNBT();
            BlockState targetBlock = context.getWorld().getBlockState(context.getPos());
            if (!currentData.contains("Filter") || NBTUtil.readBlockState(currentData.getCompound("Filter")) != targetBlock) {
                currentData.put("Filter", NBTUtil.writeBlockState(targetBlock));
                itemStack.setTag(currentData);
                context.getPlayer().sendStatusMessage(new StringTextComponent("Filter block set!").applyTextStyle(TextFormatting.GREEN), true);
                return ActionResultType.SUCCESS;
            } else if (NBTUtil.readBlockState(currentData.getCompound("Filter")) != targetBlock) {
                context.getPlayer().sendStatusMessage(new StringTextComponent("Filter block already set to this!").applyTextStyle(TextFormatting.RED), true);
            }
            return ActionResultType.PASS;
        } else return super.onItemUse(context);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        String filterBlock = stack.hasTag() && stack.getTag().contains("Filter") ? new TranslationTextComponent(NBTUtil.readBlockState(stack.getTag().getCompound("Filter")).getBlock().getTranslationKey()).getUnformattedComponentText() : "None";
        tooltip.add(new StringTextComponent("Current Filter: " + filterBlock).applyTextStyle(TextFormatting.GRAY));
        tooltip.add(new StringTextComponent("Shift-click a block to change").applyTextStyle(TextFormatting.GRAY));
    }
}
