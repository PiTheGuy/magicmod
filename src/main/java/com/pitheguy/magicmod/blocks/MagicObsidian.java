package com.pitheguy.magicmod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class MagicObsidian extends Block {

    public MagicObsidian() {
        super(Properties.create(Material.IRON)
                .hardnessAndResistance(110.0f,2500.0f)
                .sound(SoundType.METAL)
                .harvestLevel(5)
                .harvestTool(ToolType.PICKAXE)
        );
    }
}
