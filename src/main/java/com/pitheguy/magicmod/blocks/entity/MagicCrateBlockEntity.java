package com.pitheguy.magicmod.blocks.entity;

import com.pitheguy.magicmod.blocks.MagicCrate;
import com.pitheguy.magicmod.container.MagicCrateContainer;
import com.pitheguy.magicmod.init.ModTileEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;

public class MagicCrateBlockEntity extends RandomizableContainerBlockEntity {
    private NonNullList<ItemStack> crateContents = NonNullList.withSize(88, ItemStack.EMPTY);
    protected int openCount;
    private final IItemHandlerModifiable items = createHandler();
    private LazyOptional<IItemHandlerModifiable> itemHandler = LazyOptional.of(() -> items);

    public MagicCrateBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    public MagicCrateBlockEntity(BlockPos pos, BlockState state) {
        this(ModTileEntityTypes.MAGIC_CRATE.get(), pos, state);
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
    protected Component getDefaultName() {
        return new TranslatableComponent("container.magicmod.magic_crate");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return new MagicCrateContainer(id, player, this);
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        if (!this.trySaveLootTable(compound)) {
            ContainerHelper.saveAllItems(compound, this.crateContents);
        }
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.crateContents = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(compound)) {
            ContainerHelper.loadAllItems(compound, this.crateContents);
        }
    }

    private void playSound(SoundEvent sound) {
        double dx = (double) this.worldPosition.getX() + 0.5D;
        double dy = (double) this.worldPosition.getY() + 0.5D;
        double dz = (double) this.worldPosition.getZ() + 0.5D;
        this.level.playSound(null, dx, dy, dz, sound, SoundSource.BLOCKS, 0.5f,
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
    public void startOpen(Player player) {
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
    public void stopOpen(Player player) {
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

    public static int getOpenCount(BlockGetter reader, BlockPos pos) {
        BlockState blockstate = reader.getBlockState(pos);
        if (blockstate.hasBlockEntity()) {
            BlockEntity blockEntity = reader.getBlockEntity(pos);
            if (blockEntity instanceof MagicCrateBlockEntity) {
                return ((MagicCrateBlockEntity) blockEntity).openCount;
            }
        }
        return 0;
    }

    public static void swapContents(MagicCrateBlockEntity te, MagicCrateBlockEntity otherTe) {
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
