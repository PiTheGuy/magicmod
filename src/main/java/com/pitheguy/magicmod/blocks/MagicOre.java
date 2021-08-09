package com.pitheguy.magicmod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class MagicOre extends Block {

    public MagicOre() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(4.0f,5.0f)
                .sound(SoundType.STONE)
                .harvestLevel(3)
                .harvestTool(ToolType.PICKAXE)
        );
    }
}
