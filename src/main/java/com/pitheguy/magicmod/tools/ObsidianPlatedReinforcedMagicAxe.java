package com.pitheguy.magicmod.tools;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.List;

import static net.minecraft.world.level.block.Blocks.*;

public class ObsidianPlatedReinforcedMagicAxe extends MagicAxe{
    public static final List<Block> WOOD_BLOCKS = Arrays.asList(OAK_LOG, OAK_LEAVES, BIRCH_LOG, BIRCH_LEAVES, SPRUCE_LOG, SPRUCE_LEAVES, DARK_OAK_LOG, DARK_OAK_LEAVES, JUNGLE_LOG, JUNGLE_LEAVES, ACACIA_LOG, ACACIA_LEAVES);
    public ObsidianPlatedReinforcedMagicAxe(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (WOOD_BLOCKS.contains(state.getBlock())) recursivelyMineTree(worldIn, pos, entityLiving, 0);
        return super.mineBlock(stack, worldIn, state, pos, entityLiving);
    }

    public void recursivelyMineTree(Level worldIn, BlockPos pos, LivingEntity entity, int depth) {
        if (!WOOD_BLOCKS.contains(worldIn.getBlockState(pos).getBlock()) || depth > 1000) return;
        worldIn.destroyBlock(pos, true, entity);
        for (Direction direction : Direction.values()) {
            this.recursivelyMineTree(worldIn, pos.relative(direction), entity, depth + 1);
        }
    }
}
