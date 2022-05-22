package com.pitheguy.magicmod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class MagicLamp extends Block {
    public MagicLamp() {
        super(Properties.of(Material.METAL)
                .strength(2.0f, 2.0f)
                .sound(SoundType.GLASS)
                .lightLevel(state -> 15)
        );
    }
}
