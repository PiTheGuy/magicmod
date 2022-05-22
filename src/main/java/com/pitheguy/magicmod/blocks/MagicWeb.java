package com.pitheguy.magicmod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class MagicWeb extends Block {

    public MagicWeb() {
        super(Properties.of(Material.WEB)
                .strength(4.0f)
                .noCollission()
        );
    }

    @Override
    public void entityInside(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        entityIn.makeStuckInBlock(state, new Vector3d(0.05D, 0.01D, 0.05D));
    }
}
