package com.pitheguy.magicmod.armor;

import com.pitheguy.magicmod.util.RegistryHandler;
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
        if(player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == RegistryHandler.MAGIC_HELMET.get()
                && player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == RegistryHandler.MAGIC_CHESTPLATE.get() &&
                player.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem() == RegistryHandler.MAGIC_LEGGINGS.get() &&
                player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == RegistryHandler.MAGIC_BOOTS.get()
        ) {
            player.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 1, 1, true, false));
        } else if (player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == RegistryHandler.REINFORCED_MAGIC_HELMET.get()
                && player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == RegistryHandler.REINFORCED_MAGIC_CHESTPLATE.get() &&
                player.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem() == RegistryHandler.REINFORCED_MAGIC_LEGGINGS.get() &&
                player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == RegistryHandler.REINFORCED_MAGIC_BOOTS.get()
        ) {
            player.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 1, 3, true, false));
            player.addPotionEffect(new EffectInstance(Effects.REGENERATION, 2, 1, true, false));
        }
    }
}
