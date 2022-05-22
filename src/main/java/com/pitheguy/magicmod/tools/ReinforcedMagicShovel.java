package com.pitheguy.magicmod.tools;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class ReinforcedMagicShovel extends MagicShovel {
    public ReinforcedMagicShovel(IItemTier tier, float attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    @Override
    public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (worldIn.getBlockState(pos.offset(x, y, z)).getBlock().getHarvestTool(null) == ToolType.SHOVEL) {
                        worldIn.destroyBlock(pos.offset(x, y, z), true);
                    }
                }
            }
        }
        return super.mineBlock(stack, worldIn, state, pos, entityLiving);
    }
}
