package com.pitheguy.magicmod.container;

import com.pitheguy.magicmod.container.itemhandlers.SingleItemSlotItemHandler;
import com.pitheguy.magicmod.init.ModContainerTypes;
import com.pitheguy.magicmod.blocks.entity.MagicEnergizerBlockEntity;
import com.pitheguy.magicmod.util.FunctionalIntDataSlot;
import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.Objects;

import static com.pitheguy.magicmod.util.RegistryHandler.MAGIC_FUEL;

public class MagicEnergizerContainer extends AbstractContainerMenu {

    public MagicEnergizerBlockEntity tileEntity;
    private final ContainerLevelAccess canInteractWithCallable;
    public FunctionalIntDataSlot fuel;

    //Server constructor
    public MagicEnergizerContainer(final int windowID, final Inventory playerInv, final MagicEnergizerBlockEntity tile) {
        super(ModContainerTypes.MAGIC_ENERGIZER.get(), windowID);
        this.canInteractWithCallable = ContainerLevelAccess.create(tile.getLevel(), tile.getBlockPos());
        this.tileEntity = tile;
        final int slotSizePlus2 = 18;
        final int startX = 8;
        //Magic Energizer Inventory
        this.addSlot(new SingleItemSlotItemHandler(tile.getInventory(), 0, 15, 20, MAGIC_FUEL.get()));
        this.addDataSlot(fuel = new FunctionalIntDataSlot(() -> this.tileEntity.fuel,
                value -> this.tileEntity.fuel = value));
        //Hotbar
        int hotbarY = 110;
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(playerInv, col, (startX + (col * slotSizePlus2)), hotbarY));
        }
        //Main Player Inventory
        int startY = 52;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(playerInv, 9+(row*9)+col, startX + (col * slotSizePlus2), startY + (row * slotSizePlus2)));
            }
        }

    }
    //Client constructor
    public MagicEnergizerContainer(final int windowID, final Inventory playerInv, final FriendlyByteBuf data) {
        this(windowID, playerInv, getTileEntity(playerInv, data));
    }

    private static MagicEnergizerBlockEntity getTileEntity(final Inventory playerInv, final FriendlyByteBuf data) {
        Objects.requireNonNull(playerInv, "playerInv cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final BlockEntity tileAtPos = playerInv.player.level.getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof MagicEnergizerBlockEntity) {
            return (MagicEnergizerBlockEntity) tileAtPos;
        }
        throw new IllegalStateException("TileEntity is not correct " + tileAtPos);
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(canInteractWithCallable, playerIn, RegistryHandler.MAGIC_ENERGIZER.get());
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(final Player player, final int index) {
        ItemStack returnStack = ItemStack.EMPTY;
        final Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            final ItemStack slotStack = slot.getItem();
            returnStack = slotStack.copy();

            final int containerSlots = this.slots.size() - player.getInventory().items.size();
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
        return this.fuel.get() != 0 && this.tileEntity.fuel != 0 ? this.fuel.get() * 112 / MagicEnergizerBlockEntity.MAX_FUEL : 0;
    }

}
