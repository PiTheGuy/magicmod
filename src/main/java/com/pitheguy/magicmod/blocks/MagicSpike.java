package com.pitheguy.magicmod.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MagicSpike extends Block {

    public MagicSpike() {
        super(Properties.of(Material.METAL)
                .strength(2.0f, 2.0f)
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops()
        );
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return this.getShape(state, worldIn, pos, context);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        VoxelShape result = Shapes.empty();
        for (int i = 0; i < 8; i++) {
            result = Shapes.or(result, Block.box(i, i*2, i, 16 - i, i*2 + 2, 16 - i));
        }
        return result;
    }

    @Override
    public void fallOn(Level worldIn, BlockState state, BlockPos pos, Entity entityIn, float fallDistance) {
        super.fallOn(worldIn, state, pos, entityIn, fallDistance * 10);
    }
}
