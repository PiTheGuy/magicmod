package com.pitheguy.magicmod.items;

import com.pitheguy.magicmod.MagicMod;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class FilterUpgradeItem extends UpgradeItem {
    public FilterUpgradeItem() {
        super(new Item.Properties().tab(MagicMod.TAB).stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer().getPose() == Pose.CROUCHING) {
            ItemStack itemStack = context.getItemInHand();
            CompoundTag currentData = itemStack.hasTag() ? itemStack.getTag() : new CompoundTag();
            BlockState targetBlock = context.getLevel().getBlockState(context.getClickedPos());
            if (!currentData.contains("Filter") || NbtUtils.readBlockState(currentData.getCompound("Filter")) != targetBlock) {
                currentData.put("Filter", NbtUtils.writeBlockState(targetBlock));
                itemStack.setTag(currentData);
                context.getPlayer().displayClientMessage(new TextComponent("Filter block set!").withStyle(ChatFormatting.GREEN), true);
                return InteractionResult.SUCCESS;
            } else if (NbtUtils.readBlockState(currentData.getCompound("Filter")) != targetBlock) {
                context.getPlayer().displayClientMessage(new TextComponent("Filter block already set to this!").withStyle(ChatFormatting.RED), true);
            }
            return InteractionResult.PASS;
        } else return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        String filterBlock = stack.hasTag() && stack.getTag().contains("Filter") ? new TranslatableComponent(NbtUtils.readBlockState(stack.getTag().getCompound("Filter")).getBlock().getDescriptionId()).getString() : "None";
        tooltip.add(new TextComponent("Current Filter: " + filterBlock).withStyle(ChatFormatting.GRAY));
        tooltip.add(new TextComponent("Shift-click a block to change").withStyle(ChatFormatting.GRAY));
    }
}
