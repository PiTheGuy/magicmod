package com.pitheguy.magicmod.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ReinforcedMagicShovel extends MagicShovel {
    public ReinforcedMagicShovel(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (worldIn.getBlockState(pos.offset(x, y, z)).is(BlockTags.MINEABLE_WITH_SHOVEL)) {
                        worldIn.destroyBlock(pos.offset(x, y, z), true);
                    }
                }
            }
        }
        return super.mineBlock(stack, worldIn, state, pos, entityLiving);
    }
}
