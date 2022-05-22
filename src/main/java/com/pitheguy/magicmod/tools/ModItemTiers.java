package com.pitheguy.magicmod.tools;

import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum ModItemTiers implements IItemTier {
    MAGIC(() -> Ingredient.of(RegistryHandler.MAGIC_GEM.get()), 5, 75000, 42, 0, 40),
    REINFORCED_MAGIC(() -> Ingredient.of(RegistryHandler.MAGIC_GEM.get()), 6, 2000, 75, 0, 60),
    OBSIDIAN_PLATED_REINFORCED_MAGIC(() -> Ingredient.of(RegistryHandler.MAGIC_GEM.get()), 7, 550000, 135, 0, 80);

    private final int level;
    private final int uses;
    private final float speed;
    private final float attackDamageBonus;
    private final int enchantmentValue;
    private final Supplier<Ingredient> repairIngredient;

    ModItemTiers(Supplier<Ingredient> repairIngredient, int level, int uses, float speed, float attackDamageBonus, int enchantmentValue) {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.attackDamageBonus = attackDamageBonus;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getUses() {
        return uses;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return attackDamageBonus;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    public @NotNull Ingredient getRepairIngredient() {
        return repairIngredient.get();
    }
}
