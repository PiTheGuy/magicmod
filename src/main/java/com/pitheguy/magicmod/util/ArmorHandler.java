package com.pitheguy.magicmod.util;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;

public class ArmorHandler {
    public static boolean isWearingMagicArmor (Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).getItem() == RegistryHandler.MAGIC_HELMET.get() &&
                player.getItemBySlot(EquipmentSlot.CHEST).getItem() == RegistryHandler.MAGIC_CHESTPLATE.get() &&
                player.getItemBySlot(EquipmentSlot.LEGS).getItem() == RegistryHandler.MAGIC_LEGGINGS.get() &&
                player.getItemBySlot(EquipmentSlot.FEET).getItem() == RegistryHandler.MAGIC_BOOTS.get();
    }
    public static boolean isWearingReinforcedMagicArmor (Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).getItem() == RegistryHandler.REINFORCED_MAGIC_HELMET.get() &&
                player.getItemBySlot(EquipmentSlot.CHEST).getItem() == RegistryHandler.REINFORCED_MAGIC_CHESTPLATE.get() &&
                player.getItemBySlot(EquipmentSlot.LEGS).getItem() == RegistryHandler.REINFORCED_MAGIC_LEGGINGS.get() &&
                player.getItemBySlot(EquipmentSlot.FEET).getItem() == RegistryHandler.REINFORCED_MAGIC_BOOTS.get();
    }
    public static boolean isWearingObsidianPlatedReinforcedMagicArmor (Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).getItem() == RegistryHandler.OBSIDIAN_PLATED_REINFORCED_MAGIC_HELMET.get() &&
                player.getItemBySlot(EquipmentSlot.CHEST).getItem() == RegistryHandler.OBSIDIAN_PLATED_REINFORCED_MAGIC_CHESTPLATE.get() &&
                player.getItemBySlot(EquipmentSlot.LEGS).getItem() == RegistryHandler.OBSIDIAN_PLATED_REINFORCED_MAGIC_LEGGINGS.get() &&
                player.getItemBySlot(EquipmentSlot.FEET).getItem() == RegistryHandler.OBSIDIAN_PLATED_REINFORCED_MAGIC_BOOTS.get();
    }
}
