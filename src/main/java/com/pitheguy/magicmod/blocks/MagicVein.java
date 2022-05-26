package com.pitheguy.magicmod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MagicVein extends Block {
    public MagicVein() {
        super(Properties.of(Material.METAL)
                .strength(5, 7)
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops()
                .dynamicShape()
                .noCollission()
        );
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }
}