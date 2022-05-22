package com.pitheguy.magicmod.tools;

import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.tags.BlockTags;
import org.jetbrains.annotations.NotNull;

public class MagicSword extends SwordItem {
    public MagicSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    @Override
    public float getDestroySpeed(@NotNull ItemStack stack, BlockState state) {
        Block block = state.getBlock();
        if (block == Blocks.COBWEB || block == RegistryHandler.MAGIC_WEB.get()) {
            return 15.0F;
        } else {
            Material material = state.getMaterial();
            return material != Material.PLANT && material != Material.REPLACEABLE_PLANT && material != Material.CORAL && !state.is(BlockTags.LEAVES) && material != Material.VEGETABLE ? 1.0F : 1.5F;
        }
    }
}
