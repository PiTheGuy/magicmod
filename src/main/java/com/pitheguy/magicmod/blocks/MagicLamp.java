package com.pitheguy.magicmod.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class MagicLamp extends Block {
    public MagicLamp() {
        super(Properties.of(Material.METAL)
                .strength(2.0f, 2.0f)
                .sound(SoundType.GLASS)
                .lightLevel(state -> 15)
        );
    }
}
