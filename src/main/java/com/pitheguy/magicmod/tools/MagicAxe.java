package com.pitheguy.magicmod.tools;

import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MagicAxe extends AxeItem {
    public MagicAxe(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (entityLiving instanceof Player player) {
            if (Math.random() < EnchantmentHelper.getEnchantmentLevel(RegistryHandler.MAGIC_FINDER.get(), player) * 0.1) {
                Level world = player.level;
                world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(RegistryHandler.MAGIC_POWDER.get())));
            }
        }
        return super.mineBlock(stack, worldIn, state, pos, entityLiving);
    }
}
