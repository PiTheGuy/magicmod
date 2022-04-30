package com.pitheguy.magicmod.tools;

import com.pitheguy.magicmod.MagicMod;
import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MagicShovel extends ShovelItem {
    public MagicShovel(IItemTier tier, float attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityLiving;
            if (Math.random() < EnchantmentHelper.getEnchantmentLevel(RegistryHandler.MAGIC_FINDER.get(), player.getHeldItemMainhand()) * 0.1) {
                World world = player.getEntityWorld();
                world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(RegistryHandler.MAGIC_POWDER.get())));
            }
        }
        return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }
}
