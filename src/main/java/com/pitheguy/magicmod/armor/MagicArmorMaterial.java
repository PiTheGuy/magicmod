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

public enum MagicArmorMaterial implements IArmorMaterial {

    MAGIC(new int[] {50,105,125,50}, () -> { return Ingredient.fromItems(RegistryHandler.MAGIC_GEM.get()); });

    private static final int[] MAX_DAMAGE_ARRAY = new int[] {11,16,15,13};
    private final String name;
    private final int maxDamageFactor;
    private final int[] damageReductionAmountArray;
    private final int enchantability;
    private final SoundEvent soundEvent;
    private final float toughness;
    private final Supplier<Ingredient> repairMaterial;

    MagicArmorMaterial(int[] damageReductionAmountArray, Supplier<Ingredient> repairMaterial){
        this.name = "magicmod:magic";
        this.maxDamageFactor = 1200;
        this.damageReductionAmountArray = damageReductionAmountArray;
        this.enchantability = 40;
        this.soundEvent = SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND;
        this.toughness = (float) 20.0;
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
