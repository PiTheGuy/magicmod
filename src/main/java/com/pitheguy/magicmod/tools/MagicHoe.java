package com.pitheguy.magicmod.tools;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MagicHoe extends HoeItem {
    public MagicHoe(IItemTier tier, float attackSpeedIn, Properties builder) {
        super(tier, attackSpeedIn, builder);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos blockpos = context.getPos();
        int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(context);
        if (hook != 0) return hook > 0 ? ActionResultType.SUCCESS : ActionResultType.FAIL;
        if (context.getFace() != Direction.DOWN) {
            int tillX = -1;
            int blocksTilled = 0;
            int tillZ;
            while (tillX <= 1) {
                tillZ = -1;
                while (tillZ <= 1) {
                    BlockPos tillpos = blockpos.add(tillX,0,tillZ);
                    BlockState blockstate = HOE_LOOKUP.get(world.getBlockState(tillpos).getBlock());
                    if (blockstate != null && world.isAirBlock(tillpos.up())) {
                        PlayerEntity playerentity = context.getPlayer();
                        world.playSound(playerentity, tillpos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        if (!world.isRemote) {
                            world.setBlockState(tillpos, blockstate, 11);
                            blocksTilled++;
                            if (playerentity != null && blocksTilled == 1) {
                                context.getItem().damageItem(1, playerentity, (p_220043_1_) -> {
                                    p_220043_1_.sendBreakAnimation(context.getHand());
                                });
                            }
                        }
                    }
                    tillZ++;
                }
                tillX++;
            }
            if (blocksTilled > 0) {
                return ActionResultType.SUCCESS;
            }
        }

        return ActionResultType.PASS;
    }
}
