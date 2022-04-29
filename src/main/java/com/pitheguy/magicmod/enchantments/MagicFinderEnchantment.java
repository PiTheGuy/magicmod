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
    public boolean isTreasureEnchantment() {
        return true;
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && ench != Enchantments.SILK_TOUCH;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        Item item = stack.getItem();
        return super.canApply(stack) && (item instanceof MagicShovel || item instanceof MagicPickaxe || item instanceof MagicAxe);
    }
}
