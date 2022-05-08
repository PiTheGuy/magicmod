package com.pitheguy.magicmod.container;

import com.pitheguy.magicmod.container.itemhandlers.SingleItemSlotItemHandler;
import com.pitheguy.magicmod.init.ModContainerTypes;
import com.pitheguy.magicmod.tileentity.MagicEnergizerTileEntity;
import com.pitheguy.magicmod.util.FunctionalIntReferenceHolder;
import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Objects;

import static com.pitheguy.magicmod.util.RegistryHandler.MAGIC_FUEL;

public class MagicEnergizerContainer extends Container {

    public MagicEnergizerTileEntity tileEntity;
    private final IWorldPosCallable canInteractWithCallable;
    public FunctionalIntReferenceHolder fuel;

    //Server constructor
    public MagicEnergizerContainer(final int windowID, final PlayerInventory playerInv, final MagicEnergizerTileEntity tile) {
        super(ModContainerTypes.MAGIC_ENERGIZER.get(), windowID);
        this.canInteractWithCallable = IWorldPosCallable.of(tile.getWorld(), tile.getPos());
        this.tileEntity = tile;
        final int slotSizePlus2 = 18;
        final int startX = 8;
        //Hotbar
        int hotbarY = 96;
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInv, col, (startX + (col * slotSizePlus2)), hotbarY));
        }
        //Main Player Inventory
        int startY = 38;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInv, 9+(row*9)+col, startX + (col * slotSizePlus2), startY + (row * slotSizePlus2)));
            }
        }
        //Magic Energizer Inventory
        this.addSlot(new SingleItemSlotItemHandler(tile.getInventory(), 0, 15, 12, MAGIC_FUEL.get()));
        this.trackInt(fuel = new FunctionalIntReferenceHolder(() -> this.tileEntity.fuel,
                value -> this.tileEntity.fuel = value));

    }
    //Client constructor
    public MagicEnergizerContainer(final int windowID, final PlayerInventory playerInv, final PacketBuffer data) {
        this(windowID, playerInv, getTileEntity(playerInv, data));
    }

    private static MagicEnergizerTileEntity getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPos = playerInv.player.world.getTileEntity(data.readBlockPos());
        if (tileAtPos instanceof MagicEnergizerTileEntity) {
            return (MagicEnergizerTileEntity) tileAtPos;
        }
        throw new IllegalStateException("TileEntity is not correct " + tileAtPos);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, RegistryHandler.MAGIC_ENERGIZER.get());
    }

    @Nonnull
    @Override
    public ItemStack transferStackInSlot(final PlayerEntity player, final int index) {
        ItemStack returnStack = ItemStack.EMPTY;
        final Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            final ItemStack slotStack = slot.getStack();
            returnStack = slotStack.copy();

            final int containerSlots = this.inventorySlots.size() - player.inventory.mainInventory.size();
            if (index < containerSlots) {
                if (!mergeItemStack(slotStack, containerSlots, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!mergeItemStack(slotStack, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }
            if (slotStack.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (slotStack.getCount() == returnStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, slotStack);
        }
        return returnStack;
    }

    @OnlyIn(Dist.CLIENT)
    public int getFuelScaled() {
        return this.fuel.get() != 0 && this.tileEntity.fuel != 0 ? this.fuel.get() * 112 / MagicEnergizerTileEntity.MAX_FUEL : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public int getFuelConsumption() {
        return this.tileEntity.fuelConsumptionPerTick;
    }
}
