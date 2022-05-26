package com.pitheguy.magicmod.blockentity;

import com.pitheguy.magicmod.container.MagicLoggerContainer;
import com.pitheguy.magicmod.init.ModTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class MagicLoggerBlockEntity extends AutoActionBlockEntity implements MenuProvider {

    public MagicLoggerBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state, 15, 2, 4, MineableArea.ABOVE);
    }

    public MagicLoggerBlockEntity(BlockPos pos, BlockState state) {
        this(ModTileEntityTypes.MAGIC_LOGGER.get(), pos, state);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(final int windowId, final Inventory playerInv, final Player playerIn) {
        return new MagicLoggerContainer(windowId, playerInv, this);
    }

    protected Component getDefaultName() {
        return new TranslatableComponent("container.magicmod.magic_logger");
    }

    @Override
    protected boolean canMineBlock(BlockState blockState) {
        return blockState.is(BlockTags.LOGS);
    }
}
