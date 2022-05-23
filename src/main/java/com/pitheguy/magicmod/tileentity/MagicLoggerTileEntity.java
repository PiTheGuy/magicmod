package com.pitheguy.magicmod.tileentity;

import com.pitheguy.magicmod.container.MagicLoggerContainer;
import com.pitheguy.magicmod.init.ModTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class MagicLoggerTileEntity extends AutoActionTileEntity implements ITickableTileEntity, INamedContainerProvider {

    public MagicLoggerTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn, 15, 2, 4, MineableArea.ABOVE);
    }

    public MagicLoggerTileEntity() {
        this(ModTileEntityTypes.MAGIC_LOGGER.get());
    }

    @Nullable
    @Override
    public Container createMenu(final int windowId, final PlayerInventory playerInv, final PlayerEntity playerIn) {
        return new MagicLoggerContainer(windowId, playerInv, this);
    }

    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.magicmod.magic_logger");
    }

    @Override
    protected boolean canMineBlock(BlockState blockState) {
        return blockState.getBlock().is(BlockTags.LOGS);
    }
}
