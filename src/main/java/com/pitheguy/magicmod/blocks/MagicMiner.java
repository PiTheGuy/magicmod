package com.pitheguy.magicmod.blocks;

import com.pitheguy.magicmod.init.ModTileEntityTypes;
import com.pitheguy.magicmod.tileentity.MagicMinerTileEntity;
import com.pitheguy.magicmod.util.ModItemHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MagicMiner extends Block {

    public MagicMiner() {
        super(Properties.of(Material.METAL)
                .strength(4.5f, 8.0f)
                .sound(SoundType.METAL)
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE)
        );
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntityTypes.MAGIC_MINER.get().create();
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public ActionResultType use(BlockState state, @Nonnull World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isClientSide()) {
            TileEntity tile = worldIn.getBlockEntity(pos);
            if(tile instanceof MagicMinerTileEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) player,(INamedContainerProvider) tile,pos);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        TileEntity tile = worldIn.getBlockEntity(pos);
        if(tile instanceof MagicMinerTileEntity) {
            MagicMinerTileEntity miner = (MagicMinerTileEntity) tile;
            ((ModItemHandler)miner.getInventory()).toNonNullList().forEach(item -> {
                ItemEntity itemEntity = new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), item);
                worldIn.addFreshEntity(itemEntity);
            });
            if (miner.fuelSourceTileEntity != null) {
                miner.fuelSourceTileEntity.unregisterFuelConsumer(miner);
            }
        }
        if (state.hasTileEntity() && state.getBlock() != newState.getBlock()) {
            worldIn.removeBlockEntity(pos);
        }
    }
}