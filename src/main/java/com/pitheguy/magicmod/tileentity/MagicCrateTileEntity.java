package com.pitheguy.magicmod.tileentity;

import com.pitheguy.magicmod.blocks.MagicCrate;
import com.pitheguy.magicmod.container.MagicCrateContainer;
import com.pitheguy.magicmod.init.ModTileEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;

public class MagicCrateTileEntity extends LockableLootTileEntity {
    private NonNullList<ItemStack> crateContents = NonNullList.withSize(88, ItemStack.EMPTY);
    protected int openCount;
    private final IItemHandlerModifiable items = createHandler();
    private LazyOptional<IItemHandlerModifiable> itemHandler = LazyOptional.of(() -> items);

    public MagicCrateTileEntity(TileEntityType<?> typeIn) {
        super(typeIn);
    }

    public MagicCrateTileEntity() {
        this(ModTileEntityTypes.MAGIC_CRATE.get());
    }

    @Override
    public int getContainerSize() {
        return 88;
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.crateContents;
    }

    @Override
    public void setItems(NonNullList<ItemStack> itemsIn) {
        this.crateContents = itemsIn;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.magicmod.magic_crate");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new MagicCrateContainer(id, player, this);
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        if (!this.trySaveLootTable(compound)) {
            ItemStackHelper.saveAllItems(compound, this.crateContents);
        }
        return compound;
    }

    @Override
    public void load(BlockState state, CompoundNBT compound) {
        super.load(state, compound);
        this.crateContents = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(compound)) {
            ItemStackHelper.loadAllItems(compound, this.crateContents);
        }
    }

    private void playSound(SoundEvent sound) {
        double dx = (double) this.worldPosition.getX() + 0.5D;
        double dy = (double) this.worldPosition.getY() + 0.5D;
        double dz = (double) this.worldPosition.getZ() + 0.5D;
        this.level.playSound(null, dx, dy, dz, sound, SoundCategory.BLOCKS, 0.5f,
                this.level.getRandom().nextFloat() * 0.1f + 0.9f);
    }

    @Override
    public boolean triggerEvent(int id, int type) {
        if (id == 1) {
            this.openCount = type;
            return true;
        } else {
            return super.triggerEvent(id, type);
        }
    }

    @Override
    public void startOpen(PlayerEntity player) {
        if (!player.isSpectator()) {
            if (this.openCount < 0) {
                this.openCount = 0;
            }

            ++this.openCount;
            this.signalOpenCount();
        }
        playSound(SoundEvents.BARREL_OPEN);
    }

    @Override
    public void stopOpen(PlayerEntity player) {
        if (!player.isSpectator()) {
            --this.openCount;
            this.signalOpenCount();
        }
        playSound(SoundEvents.BARREL_CLOSE);
    }

    protected void signalOpenCount() {
        Block block = this.getBlockState().getBlock();
        if (block instanceof MagicCrate) {
            this.level.blockEvent(this.worldPosition, block, 1, this.openCount);
            this.level.updateNeighborsAt(this.worldPosition, block);
        }
    }

    public static int getOpenCount(IBlockReader reader, BlockPos pos) {
        BlockState blockstate = reader.getBlockState(pos);
        if (blockstate.hasTileEntity()) {
            TileEntity tileentity = reader.getBlockEntity(pos);
            if (tileentity instanceof MagicCrateTileEntity) {
                return ((MagicCrateTileEntity) tileentity).openCount;
            }
        }
        return 0;
    }

    public static void swapContents(MagicCrateTileEntity te, MagicCrateTileEntity otherTe) {
        NonNullList<ItemStack> list = te.getItems();
        te.setItems(otherTe.getItems());
        otherTe.setItems(list);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        if (this.itemHandler != null) {
            this.itemHandler.invalidate();
            this.itemHandler = null;
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nonnull Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    private IItemHandlerModifiable createHandler() {
        return new InvWrapper(this);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if(itemHandler != null) {
            itemHandler.invalidate();
        }
    }

}
