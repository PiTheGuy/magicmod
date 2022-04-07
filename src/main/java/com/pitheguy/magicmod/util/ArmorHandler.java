package com.pitheguy.magicmod.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class ArmorHandler {
    public static boolean isWearingMagicArmor (PlayerEntity player) {
        return player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == RegistryHandler.MAGIC_HELMET.get()
                && player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == RegistryHandler.MAGIC_CHESTPLATE.get() &&
                player.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem() == RegistryHandler.MAGIC_LEGGINGS.get() &&
                player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == RegistryHandler.MAGIC_BOOTS.get();
    }
    public static boolean isWearingReinforcedMagicArmor (PlayerEntity player) {
        return player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == RegistryHandler.REINFORCED_MAGIC_HELMET.get()
                && player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem() == RegistryHandler.REINFORCED_MAGIC_CHESTPLATE.get() &&
                player.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem() == RegistryHandler.REINFORCED_MAGIC_LEGGINGS.get() &&
                player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem() == RegistryHandler.REINFORCED_MAGIC_BOOTS.get();
    }
}
