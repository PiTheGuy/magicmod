package com.pitheguy.magicmod.blocks;

import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class MagicSlab extends SlabBlock {

    public MagicSlab() {
        super(Properties.create(Material.IRON)
                .hardnessAndResistance(6.5f,8.0f)
                .sound(SoundType.METAL)
                .harvestLevel(4)
                .harvestTool(ToolType.PICKAXE)
        );
    }
}
