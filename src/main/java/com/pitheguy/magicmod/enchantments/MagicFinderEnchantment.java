package com.pitheguy.magicmod.enchantments;

import com.pitheguy.magicmod.tools.MagicAxe;
import com.pitheguy.magicmod.tools.MagicPickaxe;
import com.pitheguy.magicmod.tools.MagicShovel;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MagicFinderEnchantment extends Enchantment {
    public MagicFinderEnchantment(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots) {
        super(rarityIn, typeIn, slots);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    protected boolean checkCompatibility(Enchantment ench) {
        return super.checkCompatibility(ench) && ench != Enchantments.SILK_TOUCH;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        Item item = stack.getItem();
        return super.canEnchant(stack) && (item instanceof MagicShovel || item instanceof MagicPickaxe || item instanceof MagicAxe);
    }
}
