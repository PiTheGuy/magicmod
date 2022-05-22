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
        this.canInteractWithCallable = IWorldPosCallable.create(tile.getLevel(), tile.getBlockPos());
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
        this.addSlot(new MultiItemSlotItemHandler(tile.getInventory(), 0, 27, 19, Arrays.asList(REINFORCED_MAGIC_HELMET.get(),REINFORCED_MAGIC_CHESTPLATE.get(),REINFORCED_MAGIC_LEGGINGS.get(),REINFORCED_MAGIC_BOOTS.get(),REINFORCED_MAGIC_PICKAXE.get(),REINFORCED_MAGIC_AXE.get(),REINFORCED_MAGIC_SHOVEL.get(),REINFORCED_MAGIC_SWORD.get(),REINFORCED_MAGIC_HOE.get())));
        this.addSlot(new SingleItemSlotItemHandler(tile.getInventory(), 1, 76, 19, OBSIDIAN_PLATE.get()));
        this.addSlot(new MultiItemSlotItemHandler(tile.getInventory(), 2, 134,19, Arrays.asList(OBSIDIAN_PLATED_REINFORCED_MAGIC_HELMET.get(), OBSIDIAN_PLATED_REINFORCED_MAGIC_CHESTPLATE.get(), OBSIDIAN_PLATED_REINFORCED_MAGIC_LEGGINGS.get(), OBSIDIAN_PLATED_REINFORCED_MAGIC_BOOTS.get(), OBSIDIAN_PLATED_REINFORCED_MAGIC_PICKAXE.get(), OBSIDIAN_PLATED_REINFORCED_MAGIC_AXE.get(), OBSIDIAN_PLATED_REINFORCED_MAGIC_SHOVEL.get(), OBSIDIAN_PLATED_REINFORCED_MAGIC_SWORD.get(), OBSIDIAN_PLATED_REINFORCED_MAGIC_HOE.get())));
        this.addSlot(new MultiItemSlotItemHandler(tile.getInventory(), 3,15,53, Arrays.asList(MAGIC_GEM.get(), MAGIC_BLOCK_ITEM.get())));

        this.addDataSlot(fuel = new FunctionalIntReferenceHolder(() -> this.tileEntity.fuel,
                value -> this.tileEntity.fuel = value));

    }
    //Client constructor
    public MagicPressContainer(final int windowID, final PlayerInventory playerInv, final PacketBuffer data) {
        this(windowID, playerInv, getTileEntity(playerInv, data));
    }

    private static MagicPressTileEntity getTileEntity(final PlayerInventory playerInv, final PacketBuffer data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPos = playerInv.player.level.getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof MagicPressTileEntity) {
            return (MagicPressTileEntity) tileAtPos;
        }
        throw new IllegalStateException("TileEntity is not correct " + tileAtPos);
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return stillValid(canInteractWithCallable, playerIn, RegistryHandler.MAGIC_PRESS.get());
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(final PlayerEntity player, final int index) {
        ItemStack returnStack = ItemStack.EMPTY;
        final Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            final ItemStack slotStack = slot.getItem();
            returnStack = slotStack.copy();

            final int containerSlots = this.slots.size() - player.inventory.items.size();
            if (index < containerSlots) {
                if (!moveItemStackTo(slotStack, containerSlots, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(slotStack, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }
            if (slotStack.getCount() == 0) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
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
