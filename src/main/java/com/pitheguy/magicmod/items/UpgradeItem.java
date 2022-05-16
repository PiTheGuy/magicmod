package com.pitheguy.magicmod.items;

import com.pitheguy.magicmod.MagicMod;
import net.minecraft.item.Item;

public class UpgradeItem extends Item {
    public UpgradeItem() {
        this(new Item.Properties().group(MagicMod.TAB));
    }

    public UpgradeItem(Item.Properties properties) {
        super(properties);
    }
}
