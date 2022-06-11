package com.pitheguy.magicmod.armor;

import com.pitheguy.magicmod.util.ArmorHandler;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CustomArmorMagic extends ArmorItem {
    public CustomArmorMagic(ArmorMaterial materialIn, EquipmentSlot slot, Properties builder) {
        super(materialIn, slot, builder);
    }

    @Override
    public void onArmorTick(ItemStack itemstack, Level world, Player player) {
        if (ArmorHandler.isWearingObsidianPlatedReinforcedMagicArmor(player)) {
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 2, 3, true, false));
            player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 1, 0, true, false));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1, 1, true, false));
            player.getAbilities().mayfly = true;
        } else {
            if (ArmorHandler.isWearingReinforcedMagicArmor(player))
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 2, 1, true, false));
            if (!player.getAbilities().instabuild && player.getAbilities().mayfly) {
                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
            }
        }
    }
}
