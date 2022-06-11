package com.pitheguy.magicmod.items;

import com.pitheguy.magicmod.MagicMod;
import com.pitheguy.magicmod.entities.MagicPearlEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MagicPearl extends Item {
    public MagicPearl() {
        super(new Properties().tab(MagicMod.TAB));
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        worldIn.playSound(null, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (worldIn.getRandom().nextFloat() * 0.4F + 0.8F));
        playerIn.getCooldowns().addCooldown(this, 45);
        if (!worldIn.isClientSide) {
            MagicPearlEntity magicPearlEntity = new MagicPearlEntity(worldIn, playerIn);
            magicPearlEntity.setItem(itemstack);
            magicPearlEntity.shootFromRotation(playerIn, playerIn.getXRot(), playerIn.getYRot(), 0.0F, 2.0F, 0.5F);
            worldIn.addFreshEntity(magicPearlEntity);
        }

        playerIn.awardStat(Stats.ITEM_USED.get(this));

        return InteractionResultHolder.success(itemstack);
    }
}
