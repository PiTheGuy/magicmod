package com.pitheguy.magicmod.tools;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Arrays;
import java.util.List;

public class MagicHoe extends HoeItem {
    public MagicHoe(IItemTier tier, int attackDamage, float attackSpeedIn, Properties builder) {
        super(tier, attackDamage, attackSpeedIn, builder);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        int hook = ForgeEventFactory.onHoeUse(context);
        if (hook != 0) return hook > 0 ? ActionResultType.SUCCESS : ActionResultType.FAIL;
        BlockState blockstate = TILLABLES.get(level.getBlockState(blockpos).getBlock());
        if (context.getClickedFace() != Direction.DOWN && blockstate != null && level.getBlockState(blockpos.above()).isAir()) {
            int tillX = -1;
            int blocksTilled = 0;
            int tillZ;
            while (tillX <= 1) {
                tillZ = -1;
                while (tillZ <= 1) {
                    BlockPos tillpos = new BlockPos(Vector3d.atCenterOf(blockpos).add(tillX,0,tillZ));
                    blockstate = TILLABLES.get(level.getBlockState(tillpos).getBlock());
                    if (blockstate != null && level.getBlockState(tillpos.above()).isAir()) {
                        PlayerEntity playerentity = context.getPlayer();
                        level.playSound(playerentity, tillpos, SoundEvents.HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        if (!level.isClientSide) {
                            level.setBlock(tillpos, blockstate, 11);
                            blocksTilled++;
                            if (playerentity != null && blocksTilled == 1) {
                                context.getItemInHand().hurtAndBreak(1, playerentity, (p_220043_1_) -> p_220043_1_.broadcastBreakEvent(context.getHand()));
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

    private static final List<Block> CROP_BLOCKS_AGE_7 = Arrays.asList(Blocks.WHEAT, Blocks.CARROTS, Blocks.POTATOES);
    private static final List<Block> CROP_BLOCKS_AGE_3 = Arrays.asList(Blocks.BEETROOTS, Blocks.NETHER_WART);

    @Override
    public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockState currBlock = worldIn.getBlockState(new BlockPos(Vector3d.atCenterOf(pos).add(x, 0, z)));
                if((CROP_BLOCKS_AGE_7.contains(currBlock.getBlock()) && currBlock.getValue(BlockStateProperties.AGE_7) == 7) || (CROP_BLOCKS_AGE_3.contains(currBlock.getBlock()) && currBlock.getValue(BlockStateProperties.AGE_3) == 3)) {
                    worldIn.destroyBlock(new BlockPos(Vector3d.atCenterOf(pos).add(x, 0, z)), true);
                }
            }
        }
        return super.mineBlock(stack, worldIn, state, pos, entityLiving);
    }
}
