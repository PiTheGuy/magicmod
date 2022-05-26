package com.pitheguy.magicmod.armor;

import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {

    MAGIC("magicmod:magic", 1200, new int[] {50,105,125,50}, 40, SoundEvents.ARMOR_EQUIP_DIAMOND, 20, 0, () -> Ingredient.of(RegistryHandler.MAGIC_GEM.get())),
    REINFORCED_MAGIC("magicmod:reinforced_magic", 7500, new int[] {120,225,275,120}, 60, SoundEvents.ARMOR_EQUIP_DIAMOND, 75, 0, () -> Ingredient.of(RegistryHandler.MAGIC_GEM.get())),
    OBSIDIAN_PLATED_REINFORCED_MAGIC("magicmod:obsidian_plated_reinforced_magic", 20000, new int[] {300,550,675,300}, 80, SoundEvents.ARMOR_EQUIP_DIAMOND, 175, 0, () -> Ingredient.of(RegistryHandler.MAGIC_GEM.get()));

    private static final int[] MAX_DAMAGE_ARRAY = new int[] {11,16,15,13};
    private final String name;
    private final int maxDamageFactor;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairMaterial;

    ModArmorMaterials(String name, int maxDamageFactor, int[] damageReductionAmountArray, int enchantability, SoundEvent soundEvent, float toughness, float knockbackResistance, Supplier<Ingredient> repairMaterial){
        this.name = name;
        this.maxDamageFactor = maxDamageFactor;
        this.damageReductionAmountArray = damageReductionAmountArray;
        this.enchantability = enchantability;
        this.soundEvent = soundEvent;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getDurabilityForSlot(EquipmentSlot slotIn) {
        return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * maxDamageFactor;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlot slotIn) {
        return damageReductionAmountArray[slotIn.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return soundEvent;
    }

    @Override
    public Ingredient getRepairIngredient() {
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

    @Override
    public float getKnockbackResistance() {
        return knockbackResistance;
    }
}
