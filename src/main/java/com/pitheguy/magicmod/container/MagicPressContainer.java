package com.pitheguy.magicmod.container;

import com.pitheguy.magicmod.container.itemhandlers.MultiItemSlotItemHandler;
import com.pitheguy.magicmod.container.itemhandlers.SingleItemSlotItemHandler;
import com.pitheguy.magicmod.init.ModContainerTypes;
import com.pitheguy.magicmod.tileentity.MagicPressTileEntity;
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
import java.util.Arrays;
import java.util.Objects;

import static com.pitheguy.magicmod.util.RegistryHandler.*;

public class MagicPressContainer extends Container {

    public MagicPressTileEntity tileEntity;
    private final IWorldPosCallable canInteractWithCallable;
    public FunctionalIntReferenceHolder fuel;

    //Server constructor
    public MagicPressContainer(final int windowID, final PlayerInventory playerInv, final MagicPressTileEntity tile) {
        super(ModContainerTypes.MAGIC_PRESS.get(), windowID);
        this.canInteractWithCallable = IWorldPosCallable.of(tile.getWorld(), tile.getPos());
        this.tileEntity = tile;
        final int slotSizePlus2 = 18;
        final int startX = 8;
        //Hotbar
        int hotbarY = 139;
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInv, col, (startX + (col * slotSizePlus2)), hotbarY));
        }
        //Main Player Inventory
        int startY = 81;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInv, 9+(row*9)+col, startX + (col * slotSizePlus2), startY + (row * slotSizePlus2)));
            }
        }
        //Magic Press Inventory
        this.addSlot(new MultiItemSlotItemHandler(tile.getInventory(), 0, 27, 19, Arrays.asList(MAGIC_HELMET.get(),MAGIC_CHESTPLATE.get(),MAGIC_LEGGINGS.get(),MAGIC_BOOTS.get(),MAGIC_PICKAXE.get(),MAGIC_AXE.get(),MAGIC_SHOVEL.get(),MAGIC_SWORD.get(),MAGIC_HOE.get())));
        this.addSlot(new SingleItemSlotItemHandler(tile.getInventory(), 1, 76, 19, MAGIC_PLATE.get()));
        this.addSlot(new MultiItemSlotItemHandler(tile.getInventory(), 2, 134,19, Arrays.asList(REINFORCED_MAGIC_HELMET.get(), REINFORCED_MAGIC_CHESTPLATE.get(), REINFORCED_MAGIC_LEGGINGS.get(), REINFORCED_MAGIC_BOOTS.get(), REINFORCED_MAGIC_PICKAXE.get(), REINFORCED_MAGIC_AXE.get(), REINFORCED_MAGIC_SHOVEL.get(), REINFORCED_MAGIC_SWORD.get(), REINFORCED_MAGIC_HOE.get())));
        this.addSlot(new MultiItemSlotItemHandler(tile.getInventory(), 3,15,53, Arrays.asList(MAGIC_GEM.get(), MAGIC_BLOCK_ITEM.get())));

        this.trackInt(fuel = new FunctionalIntReferenceHolder(() -> this.tileEntity.fuel,
                value -> this.tileEntity.fuel = value));

    }
    //Client constructor
    public MagicPressContainer(final int windowID, final PlayerInventory playerInv, final PacketBuffer data) {
        this(windowID, playerInv, getTileEntity(playerInv, data));
    }

    private static MagicPressTileEntity getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPos = playerInv.player.world.getTileEntity(data.readBlockPos());
        if (tileAtPos instanceof MagicPressTileEntity) {
            return (MagicPressTileEntity) tileAtPos;
        }
        throw new IllegalStateException("TileEntity is not correct " + tileAtPos);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, RegistryHandler.MAGIC_PRESS.get());
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
        return this.fuel.get() != 0 && this.tileEntity.fuel != 0 ? this.fuel.get() * 112 / this.tileEntity.maxFuel : 0;
    }
}
