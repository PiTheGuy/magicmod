package com.pitheguy.magicmod.blocks;

import com.pitheguy.magicmod.init.ModTileEntityTypes;
import com.pitheguy.magicmod.blockentity.MagicInfuserBlockEntity;
import com.pitheguy.magicmod.util.ModItemHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import javax.annotation.Nullable;

public class MagicInfuser extends BaseEntityBlock implements EntityBlock {

    public MagicInfuser() {
        super(Block.Properties.of(Material.METAL)
                .strength(6.5f, 8.0f)
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops()
        );
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModTileEntityTypes.MAGIC_INFUSER.get().create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createInfuserTicker(level, type, ModTileEntityTypes.MAGIC_INFUSER.get());
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> createInfuserTicker(Level p_151988_, BlockEntityType<T> p_151989_, BlockEntityType<? extends MagicInfuserBlockEntity> p_151990_) {
        return p_151988_.isClientSide ? null : createTickerHelper(p_151989_, p_151990_, MagicInfuserBlockEntity::serverTick);
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (worldIn != null && !worldIn.isClientSide()) {
            BlockEntity tile = worldIn.getBlockEntity(pos);
            if(tile instanceof MagicInfuserBlockEntity) {
                NetworkHooks.openGui((ServerPlayer) player,(MenuProvider) tile,pos);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        BlockEntity tile = worldIn.getBlockEntity(pos);
        if(tile instanceof MagicInfuserBlockEntity) {
            MagicInfuserBlockEntity infuser = (MagicInfuserBlockEntity) tile;
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