package com.pitheguy.magicmod.blocks;

import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

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

    @Override
    public boolean isRandomlyTicking(BlockState p_49921_) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        //MagicMod.LOGGER.info("Magic Vein received random tick");
        if (random.nextDouble() < 0.2) {
            BlockPos targetPos = pos.relative(Direction.Plane.HORIZONTAL.getRandomDirection(random));
            if(this.canSurvive(state, level, targetPos) && level.getBlockState(targetPos).isAir()) {
                level.setBlock(targetPos, RegistryHandler.MAGIC_VEIN.get().defaultBlockState(), 3);
            }
        }
    }

    public BlockState updateShape(BlockState p_51032_, Direction p_51033_, BlockState p_51034_, LevelAccessor p_51035_, BlockPos p_51036_, BlockPos p_51037_) {
        return !p_51032_.canSurvive(p_51035_, p_51036_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_51032_, p_51033_, p_51034_, p_51035_, p_51036_, p_51037_);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isFaceSturdy(level, pos, Direction.UP);
    }
}