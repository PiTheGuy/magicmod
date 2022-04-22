package com.pitheguy.magicmod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class MagicSpike extends Block {

    public MagicSpike() {
        super(Properties.create(Material.IRON)
                .hardnessAndResistance(2.0f, 2.0f)
                .sound(SoundType.METAL)
                .harvestTool(ToolType.PICKAXE)
                .notSolid()
        );
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.getShape(state, worldIn, pos, context);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape result = VoxelShapes.empty();
        for (int i = 0; i < 8; i++) {
            result = VoxelShapes.or(result, Block.makeCuboidShape(i, i*2, i, 16 - i, i*2 + 2, 16 - i));
        }
        return result;
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        super.onFallenUpon(worldIn, pos, entityIn, fallDistance * 10);
    }
}
