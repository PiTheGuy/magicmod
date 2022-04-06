package com.pitheguy.magicmod.tools;

import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ReinforcedMagicItemTier implements IItemTier {
    REINFORCED_MAGIC(() -> {
        return Ingredient.fromItems(RegistryHandler.MAGIC_GEM.get());
    });

    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairMaterial;

    ReinforcedMagicItemTier(Supplier<Ingredient> repairMaterial) {
        this.harvestLevel = 6;
        this.maxUses = 200000;
        this.efficiency = (float) 75.0;
        this.attackDamage = (float) 0.0;
        this.enchantability = 60;
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
    public Ingredient getRepairMaterial() {
        return repairMaterial.get();
    }
}
