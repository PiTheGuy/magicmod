package com.pitheguy.magicmod.armor;

import com.pitheguy.magicmod.util.ArmorHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class CustomArmorMagic extends ArmorItem {
    public CustomArmorMagic(IArmorMaterial materialIn, EquipmentSlotType slot, Properties builder) {
        super(materialIn, slot, builder);
    }

    @Override
    public void onArmorTick(ItemStack itemstack, World world, PlayerEntity player) {
        if (ArmorHandler.isWearingObsidianPlatedReinforcedMagicArmor(player)) {
            player.addEffect(new EffectInstance(Effects.REGENERATION, 2, 3, true, false));
            player.addEffect(new EffectInstance(Effects.SATURATION, 1, 0, true, false));
            player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, 1, 1, true, false));
            player.abilities.mayfly = true;
        } else {
            if (ArmorHandler.isWearingReinforcedMagicArmor(player))
                player.addEffect(new EffectInstance(Effects.REGENERATION, 2, 1, true, false));
            if (!player.abilities.instabuild && player.abilities.mayfly) {
                player.abilities.mayfly = false;
                player.abilities.flying = false;
            }
        }
    }
}
