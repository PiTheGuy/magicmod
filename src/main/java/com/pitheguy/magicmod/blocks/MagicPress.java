package com.pitheguy.magicmod.blocks;

import com.pitheguy.magicmod.blockentity.MagicPressBlockEntity;
import com.pitheguy.magicmod.init.ModTileEntityTypes;
import com.pitheguy.magicmod.util.ModItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class MagicPress extends BaseEntityBlock implements EntityBlock {

    public MagicPress() {
        super(Properties.of(Material.METAL)
                .strength(6.5f, 8.0f)
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops()
        );
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModTileEntityTypes.MAGIC_PRESS.get().create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createPressTicker(level, type, ModTileEntityTypes.MAGIC_PRESS.get());
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createPressTicker(Level p_151988_, BlockEntityType<T> p_151989_, BlockEntityType<? extends MagicPressBlockEntity> p_151990_) {
        return p_151988_.isClientSide ? null : createTickerHelper(p_151989_, p_151990_, MagicPressBlockEntity::serverTick);
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (worldIn != null && !worldIn.isClientSide) {
            BlockEntity tile = worldIn.getBlockEntity(pos);
            if(tile instanceof MagicPressBlockEntity) {
                NetworkHooks.openGui((ServerPlayer) player,(MenuProvider) tile,pos);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        BlockEntity tile = worldIn.getBlockEntity(pos);
        if(tile instanceof MagicPressBlockEntity infuser) {
            ((ModItemHandler)infuser.getInventory()).toNonNullList().forEach(item -> {
                ItemEntity itemEntity = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), item);
                worldIn.addFreshEntity(itemEntity);
            });
        }
        if (state.hasBlockEntity() && state.getBlock() != newState.getBlock()) {
            worldIn.removeBlockEntity(pos);
        }
    }
}