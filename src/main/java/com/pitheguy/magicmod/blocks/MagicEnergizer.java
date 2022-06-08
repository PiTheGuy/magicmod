package com.pitheguy.magicmod.blocks;

import com.pitheguy.magicmod.blockentity.MagicEnergizerBlockEntity;
import com.pitheguy.magicmod.init.ModTileEntityTypes;
import com.pitheguy.magicmod.util.ModItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class MagicEnergizer extends BaseEntityBlock implements EntityBlock {

    public MagicEnergizer() {
        super(Properties.of(Material.METAL)
                .strength(6.5f, 8.0f)
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops()
        );
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MagicEnergizerBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type, (BlockEntityType<? extends MagicEnergizerBlockEntity>) ModTileEntityTypes.MAGIC_ENERGIZER.get(), (level1, pos, state1, tile) -> tile.tick());
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (worldIn != null && !worldIn.isClientSide()) {
            BlockEntity tile = worldIn.getBlockEntity(pos);
            if(tile instanceof MagicEnergizerBlockEntity) {
                NetworkHooks.openGui((ServerPlayer) player,(MenuProvider) tile,pos);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        BlockEntity tile = worldIn.getBlockEntity(pos);
        if(tile instanceof MagicEnergizerBlockEntity) {
            MagicEnergizerBlockEntity energizer = (MagicEnergizerBlockEntity) tile;
            ((ModItemHandler)energizer.getInventory()).toNonNullList().forEach(item -> {
                ItemEntity itemEntity = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), item);
                worldIn.addFreshEntity(itemEntity);
            });
        }
        if (state.hasBlockEntity() && state.getBlock() != newState.getBlock()) {
            worldIn.removeBlockEntity(pos);
        }
    }
}