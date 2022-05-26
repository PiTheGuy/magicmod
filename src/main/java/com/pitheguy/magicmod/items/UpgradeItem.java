package com.pitheguy.magicmod.items;

import com.pitheguy.magicmod.MagicMod;
import net.minecraft.world.item.Item;

public class UpgradeItem extends Item {
    public UpgradeItem() {
        this(new Item.Properties().tab(MagicMod.TAB));
    }

    public UpgradeItem(Item.Properties properties) {
        super(properties);
    }
}
