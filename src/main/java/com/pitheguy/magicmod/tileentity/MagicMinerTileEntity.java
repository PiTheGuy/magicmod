package com.pitheguy.magicmod.tileentity;

import com.pitheguy.magicmod.MagicMod;
import com.pitheguy.magicmod.container.MagicMinerContainer;
import com.pitheguy.magicmod.init.ModTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class MagicMinerTileEntity extends AutoActionTileEntity implements ITickableTileEntity, INamedContainerProvider {

    public MagicMinerTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public MagicMinerTileEntity() {
        this(ModTileEntityTypes.MAGIC_MINER.get());
    }

    @Nullable
    @Override
    public Container createMenu(final int windowId, final PlayerInventory playerInv, final PlayerEntity playerIn) {
        return new MagicMinerContainer(windowId, playerInv, this);
    }

    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.magicmod.magic_miner");
    }

    @Override
    protected boolean canMineBlock(BlockState blockState) {
        //if(!blockState.isAir()) MagicMod.LOGGER.info("Block {} has tool type of {}", blockState.getBlock(), blockState.getHarvestTool().getName());
        return blockState.getHarvestTool() == ToolType.PICKAXE || blockState.getHarvestTool() == ToolType.SHOVEL;
    }
}
