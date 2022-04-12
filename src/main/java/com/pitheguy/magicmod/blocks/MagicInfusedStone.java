package com.pitheguy.magicmod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class MagicInfusedStone extends Block {
    public MagicInfusedStone() {
        super(Properties.create(Material.IRON)
                .hardnessAndResistance(2.0f, 8.0f)
                .sound(SoundType.STONE)
                .harvestLevel(1)
                .harvestTool(ToolType.PICKAXE)
        );
    }

}
