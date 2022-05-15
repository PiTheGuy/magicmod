package com.pitheguy.magicmod.items;

import com.pitheguy.magicmod.MagicMod;
import com.pitheguy.magicmod.entities.MagicPearlEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class MagicPearl extends Item {
    public MagicPearl() {
        super(new Properties().group(MagicMod.TAB));
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        playerIn.getCooldownTracker().setCooldown(this, 45);
        if (!worldIn.isRemote) {
            MagicPearlEntity magicPearlEntity = new MagicPearlEntity(worldIn, playerIn);
            magicPearlEntity.setItem(itemstack);
            magicPearlEntity.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 2.0F, 0.5F);
            worldIn.addEntity(magicPearlEntity);
        }

        playerIn.addStat(Stats.ITEM_USED.get(this));

        return ActionResult.resultSuccess(itemstack);
    }
}
