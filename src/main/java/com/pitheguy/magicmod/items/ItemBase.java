package com.pitheguy.magicmod.items;

import com.pitheguy.magicmod.MagicMod;
import net.minecraft.item.Item;

public class ItemBase extends Item {
    public ItemBase() {
        super(new Item.Properties().tab(MagicMod.TAB));
    }
}
