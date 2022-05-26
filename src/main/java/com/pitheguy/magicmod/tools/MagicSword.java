package com.pitheguy.magicmod.tools;

import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nonnull;

public class MagicSword extends SwordItem {
    public MagicSword(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    @Override
    public float getDestroySpeed(@Nonnull ItemStack stack, BlockState state) {
        Block block = state.getBlock();
        if (block == Blocks.COBWEB || block == RegistryHandler.MAGIC_WEB.get()) {
            return 15.0F;
        } else {
            Material material = state.getMaterial();
            return material != Material.PLANT && material != Material.REPLACEABLE_PLANT && material != Material.WATER_PLANT && !state.is(BlockTags.LEAVES) && material != Material.VEGETABLE ? 1.0F : 1.5F;
        }
    }
}
