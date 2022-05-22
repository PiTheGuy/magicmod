package com.pitheguy.magicmod.items;

import com.pitheguy.magicmod.MagicMod;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Pose;
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
        super(new Item.Properties().tab(MagicMod.TAB).stacksTo(1));
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        if (context.getPlayer().getPose() == Pose.CROUCHING) {
            ItemStack itemStack = context.getItemInHand();
            CompoundNBT currentData = itemStack.hasTag() ? itemStack.getTag() : new CompoundNBT();
            BlockState targetBlock = context.getLevel().getBlockState(context.getClickedPos());
            if (!currentData.contains("Filter") || NBTUtil.readBlockState(currentData.getCompound("Filter")) != targetBlock) {
                currentData.put("Filter", NBTUtil.writeBlockState(targetBlock));
                itemStack.setTag(currentData);
                context.getPlayer().displayClientMessage(new StringTextComponent("Filter block set!").withStyle(TextFormatting.GREEN), true);
                return ActionResultType.SUCCESS;
            } else if (NBTUtil.readBlockState(currentData.getCompound("Filter")) != targetBlock) {
                context.getPlayer().displayClientMessage(new StringTextComponent("Filter block already set to this!").withStyle(TextFormatting.RED), true);
            }
            return ActionResultType.PASS;
        } else return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        String filterBlock = stack.hasTag() && stack.getTag().contains("Filter") ? new TranslationTextComponent(NBTUtil.readBlockState(stack.getTag().getCompound("Filter")).getBlock().getDescriptionId()).getString() : "None";
        tooltip.add(new StringTextComponent("Current Filter: " + filterBlock).withStyle(TextFormatting.GRAY));
        tooltip.add(new StringTextComponent("Shift-click a block to change").withStyle(TextFormatting.GRAY));
    }
}
