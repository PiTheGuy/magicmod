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
        if (ArmorHandler.isWearingReinforcedMagicArmor(player)) {
            player.addPotionEffect(new EffectInstance(Effects.REGENERATION, 2, 1, true, false));
        }
    }
}
