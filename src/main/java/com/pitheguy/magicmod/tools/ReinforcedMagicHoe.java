package com.pitheguy.magicmod.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Arrays;
import java.util.List;

public class ReinforcedMagicHoe extends MagicHoe {
    public ReinforcedMagicHoe(Tier tier, int attackDamage, float attackSpeedIn, Properties builder, int abilityRange) {
        super(tier, attackDamage, attackSpeedIn, builder, abilityRange);
    }

    private static final List<Block> CROP_BLOCKS_AGE_7 = Arrays.asList(Blocks.WHEAT, Blocks.CARROTS, Blocks.POTATOES);
    private static final List<Block> CROP_BLOCKS_AGE_3 = Arrays.asList(Blocks.BEETROOTS, Blocks.NETHER_WART);

    @Override
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                BlockState currBlock = worldIn.getBlockState(pos.offset(x, 0, z));
                if((CROP_BLOCKS_AGE_7.contains(currBlock.getBlock()) && currBlock.getValue(BlockStateProperties.AGE_7) == 7) || (CROP_BLOCKS_AGE_3.contains(currBlock.getBlock()) && currBlock.getValue(BlockStateProperties.AGE_3) == 3)) {
                    worldIn.destroyBlock(pos.offset(x, 0, z), true);
                }
            }
        }
        return super.mineBlock(stack, worldIn, state, pos, entityLiving);
    }
}
