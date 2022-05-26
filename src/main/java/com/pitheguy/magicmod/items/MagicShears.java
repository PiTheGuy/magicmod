package com.pitheguy.magicmod.items;

import com.pitheguy.magicmod.MagicMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShearsItem;

public class MagicShears extends ShearsItem {
    public MagicShears() {
        super(new Item.Properties().durability(2000).tab(MagicMod.TAB));
    }
}
