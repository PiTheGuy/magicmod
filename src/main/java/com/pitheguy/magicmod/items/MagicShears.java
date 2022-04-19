package com.pitheguy.magicmod.items;

import com.pitheguy.magicmod.MagicMod;
import net.minecraft.item.Item;
import net.minecraft.item.ShearsItem;

public class MagicShears extends ShearsItem {
    public MagicShears() {
        super(new Item.Properties().maxDamage(2000).group(MagicMod.TAB));
    }
}