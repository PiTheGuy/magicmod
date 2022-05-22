package com.pitheguy.magicmod.blocks;

import com.pitheguy.magicmod.MagicMod;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class BlockItemBase extends BlockItem {
    public BlockItemBase(Block block) {
        super(block, new Item.Properties().tab(MagicMod.TAB));
    }
}
