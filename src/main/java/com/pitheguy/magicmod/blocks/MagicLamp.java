package com.pitheguy.magicmod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class MagicLamp extends Block {
    public MagicLamp() {
        super(Properties.create(Material.IRON)
                .hardnessAndResistance(2.0f, 2.0f)
                .sound(SoundType.METAL)
                .lightValue(15)
        );
    }

}
