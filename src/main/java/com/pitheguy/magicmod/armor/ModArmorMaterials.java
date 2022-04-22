package com.pitheguy.magicmod.armor;

import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum ModArmorMaterials implements IArmorMaterial {

    MAGIC("magicmod:magic", 1200, new int[] {50,105,125,50}, 40, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 20, () -> Ingredient.fromItems(RegistryHandler.MAGIC_GEM.get())),
    REINFORCED_MAGIC("magicmod:reinforced_magic", 7500, new int[] {120,225,275,120}, 60, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 75, () -> Ingredient.fromItems(RegistryHandler.MAGIC_GEM.get())),
    OBSIDIAN_PLATED_REINFORCED_MAGIC("magicmod:obsidian_plated_reinforced_magic", 20000, new int[] {300,550,675,300}, 80, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 175, () -> Ingredient.fromItems(RegistryHandler.MAGIC_GEM.get()));

    private static final int[] MAX_DAMAGE_ARRAY = new int[] {11,16,15,13};
    private final String name;
    private final int maxDamageFactor;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final Supplier<Ingredient> repairMaterial;

    ModArmorMaterials(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float toughness, Supplier<Ingredient> repairMaterial){
        this.name = name;
        this.maxDamageFactor = maxDamageFactor;
        this.damageReductionAmountArray = damageReductionAmountArray;
        this.enchantability = enchantability;
        this.soundEvent = soundEvent;
        this.toughness = toughness;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getDurability(EquipmentSlotType slotIn) {
        return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * maxDamageFactor;
    }

    @Override
    public int getDamageReductionAmount(EquipmentSlotType slotIn) {
        return damageReductionAmountArray[slotIn.getIndex()];
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public SoundEvent getSoundEvent() {
        return soundEvent;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return repairMaterial.get();
    }
    @OnlyIn(Dist.CLIENT)
    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getToughness() {
        return toughness;
    }
}
