package com.pitheguy.magicmod.container;

import com.pitheguy.magicmod.init.ModContainerTypes;
import com.pitheguy.magicmod.tileentity.MagicLoggerTileEntity;
import com.pitheguy.magicmod.tileentity.MagicMinerTileEntity;
import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;

import java.util.Objects;

public class MagicLoggerContainer extends AutoActionContainer<MagicLoggerTileEntity> {


    //Server constructor
    public MagicLoggerContainer(final int windowID, final PlayerInventory playerInv, final MagicLoggerTileEntity tile) {
        super(windowID, playerInv, tile, ModContainerTypes.MAGIC_LOGGER.get());
    }
    //Client constructor
    public MagicLoggerContainer(final int windowID, final PlayerInventory playerInv, final PacketBuffer data) {
        this(windowID, playerInv, getTileEntity(playerInv, data));
    }

    private static MagicLoggerTileEntity getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPos = playerInv.player.level.getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof MagicLoggerTileEntity) {
            return (MagicLoggerTileEntity) tileAtPos;
        }
        throw new IllegalStateException("TileEntity is not correct " + tileAtPos);
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return stillValid(canInteractWithCallable, playerIn, RegistryHandler.MAGIC_LOGGER.get());
    }
}
