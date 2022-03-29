package com.pitheguy.magicmod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class MagicGlueBlock extends Block {
    public MagicGlueBlock() {
        super(Block.Properties.create(Material.IRON)
                .hardnessAndResistance(4.5f, 6.5f)
                .sound(SoundType.SLIME)
                .harvestLevel(3)
                .harvestTool(ToolType.PICKAXE)
                .jumpFactor(0.0f)
                .speedFactor(0.1f)
        );
    }
}
