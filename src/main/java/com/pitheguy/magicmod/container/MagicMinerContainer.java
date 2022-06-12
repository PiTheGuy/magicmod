package com.pitheguy.magicmod.container;

import com.pitheguy.magicmod.init.ModContainerTypes;
import com.pitheguy.magicmod.blocks.entity.MagicMinerBlockEntity;
import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Objects;

public class MagicMinerContainer extends AutoActionContainer<MagicMinerBlockEntity> {


    //Server constructor
    public MagicMinerContainer(final int windowID, final Inventory playerInv, final MagicMinerBlockEntity tile) {
        super(windowID, playerInv, tile, ModContainerTypes.MAGIC_MINER.get());
    }
    //Client constructor
    public MagicMinerContainer(final int windowID, final Inventory playerInv, final FriendlyByteBuf data) {
        this(windowID, playerInv, getTileEntity(playerInv, data));
    }

    private static MagicMinerBlockEntity getTileEntity(final Inventory playerInv, final FriendlyByteBuf data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final BlockEntity tileAtPos = playerInv.player.level.getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof MagicMinerBlockEntity) {
            return (MagicMinerBlockEntity) tileAtPos;
        }
        throw new IllegalStateException("TileEntity is not correct " + tileAtPos);
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(canInteractWithCallable, playerIn, RegistryHandler.MAGIC_MINER.get());
    }
}
