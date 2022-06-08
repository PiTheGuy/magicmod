package com.pitheguy.magicmod.blockentity;

import com.pitheguy.magicmod.blocks.state.ModBlockStateProperties;
import com.pitheguy.magicmod.container.MagicMinerContainer;
import com.pitheguy.magicmod.init.ModTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class MagicMinerBlockEntity extends AutoActionBlockEntity implements MenuProvider {

    public MagicMinerBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state, 5, 1, 2, MineableArea.BELOW);
    }

    public MagicMinerBlockEntity(BlockPos pos, BlockState state) {
        this(ModTileEntityTypes.MAGIC_MINER.get(), pos, state);
    }

    @Override
    public void serverTick(Level level, BlockPos pos, BlockState state, AutoActionBlockEntity tile) {
        super.serverTick(level, pos, state, tile);
        this.updateBlockState();
    }

    private void updateBlockState() {
        BlockState oldBlockState = this.level.getBlockState(this.worldPosition);
        BlockState newBlockState = oldBlockState.setValue(ModBlockStateProperties.RUNNING, this.status.isRunning());
        if (oldBlockState != newBlockState) {
            this.level.setBlock(this.worldPosition, newBlockState, 3);
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(final int windowId, final Inventory playerInv, final Player playerIn) {
        return new MagicMinerContainer(windowId, playerInv, this);
    }

    protected Component getDefaultName() {
        return new TranslatableComponent("container.magicmod.magic_miner");
    }

    @Override
    protected boolean canMineBlock(BlockState blockState) {
        //if(!block.isAir()) MagicMod.LOGGER.info("Block {} has tool type of {}", block.getBlock(), block.getHarvestTool().getName());
        return blockState.is(BlockTags.MINEABLE_WITH_PICKAXE) || blockState.is(BlockTags.MINEABLE_WITH_SHOVEL);
    }
}
