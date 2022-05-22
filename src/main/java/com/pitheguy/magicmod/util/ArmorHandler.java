package com.pitheguy.magicmod.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class ArmorHandler {
    public static boolean isWearingMagicArmor (PlayerEntity player) {
        return player.getItemBySlot(EquipmentSlotType.HEAD).getItem() == RegistryHandler.MAGIC_HELMET.get() &&
                player.getItemBySlot(EquipmentSlotType.CHEST).getItem() == RegistryHandler.MAGIC_CHESTPLATE.get() &&
                player.getItemBySlot(EquipmentSlotType.LEGS).getItem() == RegistryHandler.MAGIC_LEGGINGS.get() &&
                player.getItemBySlot(EquipmentSlotType.FEET).getItem() == RegistryHandler.MAGIC_BOOTS.get();
    }
    public static boolean isWearingReinforcedMagicArmor (PlayerEntity player) {
        return player.getItemBySlot(EquipmentSlotType.HEAD).getItem() == RegistryHandler.REINFORCED_MAGIC_HELMET.get() &&
                player.getItemBySlot(EquipmentSlotType.CHEST).getItem() == RegistryHandler.REINFORCED_MAGIC_CHESTPLATE.get() &&
                player.getItemBySlot(EquipmentSlotType.LEGS).getItem() == RegistryHandler.REINFORCED_MAGIC_LEGGINGS.get() &&
                player.getItemBySlot(EquipmentSlotType.FEET).getItem() == RegistryHandler.REINFORCED_MAGIC_BOOTS.get();
    }
    public static boolean isWearingObsidianPlatedReinforcedMagicArmor (PlayerEntity player) {
        return player.getItemBySlot(EquipmentSlotType.HEAD).getItem() == RegistryHandler.OBSIDIAN_PLATED_REINFORCED_MAGIC_HELMET.get() &&
                player.getItemBySlot(EquipmentSlotType.CHEST).getItem() == RegistryHandler.OBSIDIAN_PLATED_REINFORCED_MAGIC_CHESTPLATE.get() &&
                player.getItemBySlot(EquipmentSlotType.LEGS).getItem() == RegistryHandler.OBSIDIAN_PLATED_REINFORCED_MAGIC_LEGGINGS.get() &&
                player.getItemBySlot(EquipmentSlotType.FEET).getItem() == RegistryHandler.OBSIDIAN_PLATED_REINFORCED_MAGIC_BOOTS.get();
    }
}
