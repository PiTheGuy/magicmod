package com.pitheguy.magicmod.blocks;

import com.pitheguy.magicmod.init.ModTileEntityTypes;
import com.pitheguy.magicmod.tileentity.MagicCrateTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class MagicCrate extends Block {

    public MagicCrate() {
        super(Properties.of(Material.METAL)
                .strength(6.5f, 12.0f)
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
        return ModTileEntityTypes.MAGIC_CRATE.get().create();
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isClientSide()) {
            TileEntity tile = worldIn.getBlockEntity(pos);
            if(tile instanceof MagicCrateTileEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (MagicCrateTileEntity) tile, pos);
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
    }

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity te = worldIn.getBlockEntity(pos);
            if (te instanceof MagicCrateTileEntity) {
                InventoryHelper.dropContents(worldIn, pos, ((MagicCrateTileEntity) te).getItems());
            }
        }
    }
}