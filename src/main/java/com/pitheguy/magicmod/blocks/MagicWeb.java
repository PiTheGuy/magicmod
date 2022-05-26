package com.pitheguy.magicmod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;

public class MagicWeb extends Block {

    public MagicWeb() {
        super(Properties.of(Material.WEB)
                .strength(4.0f)
                .noCollission()
        );
    }

    @Override
    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
        entityIn.makeStuckInBlock(state, new Vec3(0.05D, 0.01D, 0.05D));
    }
}
