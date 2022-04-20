package com.pitheguy.magicmod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MagicWeb extends Block {

    public MagicWeb() {
        super(Properties.create(Material.WEB)
                .hardnessAndResistance(4.0f)
                .doesNotBlockMovement()
        );
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        entityIn.setMotionMultiplier(state, new Vec3d(0.05D, 0.01D, 0.05D));
    }
}
