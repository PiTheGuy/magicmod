package com.pitheguy.magicmod.tools;

import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum ModItemTiers implements IItemTier { //TODO: Refactor to combine enums
    MAGIC(() -> Ingredient.fromItems(RegistryHandler.MAGIC_GEM.get()), 5, 75000, 42, 0, 40),
    REINFORCED_MAGIC(() -> Ingredient.fromItems(RegistryHandler.MAGIC_GEM.get()), 6, 2000, 75, 0, 60),
    OBSIDIAN_PLATED_REINFORCED_MAGIC(() -> Ingredient.fromItems(RegistryHandler.MAGIC_GEM.get()), 7, 550000, 135, 0, 80);

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairMaterial;

    ModItemTiers(Supplier<Ingredient> repairMaterial, int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getMaxUses() {
        return maxUses;
    }

    @Override
    public float getEfficiency() {
        return efficiency;
    }

    @Override
    public float getAttackDamage() {
        return attackDamage;
    }

    @Override
    public int getHarvestLevel() {
        return harvestLevel;
    }

    @Override
    public int getEnchantability() {
        return enchantability;
    }

    @Override
    public @NotNull Ingredient getRepairMaterial() {
        return repairMaterial.get();
    }
}
