package com.pitheguy.magicmod.blocks;

import com.pitheguy.magicmod.blockentity.MagicMinerBlockEntity;
import com.pitheguy.magicmod.blocks.state.ModBlockStateProperties;
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
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MagicMiner extends BaseEntityBlock implements EntityBlock {
    public static final BooleanProperty RUNNING = ModBlockStateProperties.RUNNING;

    public MagicMiner() {
        super(Properties.of(Material.METAL)
                .strength(4.5f, 8.0f)
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops()
        );
        this.registerDefaultState(this.getStateDefinition().any().setValue(RUNNING, false));
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MagicMinerBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type, (BlockEntityType<? extends MagicMinerBlockEntity>) ModTileEntityTypes.MAGIC_MINER.get(), (level1, pos, state1, tile) -> tile.serverTick(level1, pos, state1, tile));
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public InteractionResult use(BlockState state, @Nonnull Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (!worldIn.isClientSide()) {
            BlockEntity tile = worldIn.getBlockEntity(pos);
            if(tile instanceof MagicMinerBlockEntity) {
                NetworkHooks.openGui((ServerPlayer) player,(MenuProvider) tile,pos);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        BlockEntity tile = worldIn.getBlockEntity(pos);
        if (state.hasBlockEntity() && state.getBlock() != newState.getBlock()) {
            if(tile instanceof MagicMinerBlockEntity) {
                MagicMinerBlockEntity miner = (MagicMinerBlockEntity) tile;
                ((ModItemHandler)miner.getInventory()).toNonNullList().forEach(item -> {
                    ItemEntity itemEntity = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), item);
                    worldIn.addFreshEntity(itemEntity);
                });
                if (miner.fuelSourceTileEntity != null) {
                    miner.fuelSourceTileEntity.unregisterFuelConsumer(miner);
                }
            }
            worldIn.removeBlockEntity(pos);
        }
    }

    @Override
    protected void createBlockStateDefinition(@Nonnull StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(RUNNING);
    }
}