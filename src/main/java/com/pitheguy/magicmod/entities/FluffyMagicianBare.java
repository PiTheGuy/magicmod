package com.pitheguy.magicmod.entities;

import com.pitheguy.magicmod.init.ModEntityTypes;
import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FluffyMagicianBare extends FluffyMagician {

    public static final int POWDER_REGROW_INTERVAL = 2400;

    public FluffyMagicianBare(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
        this.hasPowder = false;
        this.powderRegrowTime = 2400;
    }

    @Override
    public void recreate() {
        if (!this.level.isClientSide && hasPowder) {
            //LOGGER.info("Converting to regular Fluffy Magician");
            FluffyMagician newEntity = ModEntityTypes.FLUFFY_MAGICIAN.get().spawn(this.getServer().overworld(), null, null, null, new BlockPos(this.getPosition(0)), SpawnReason.CONVERSION, true, true);
            newEntity.restoreFrom(this);
            this.remove();
        }
    }

    @Override
    public void tick() {
        if (powderRegrowTime <= -POWDER_REGROW_INTERVAL && this.level.getBlockState(new BlockPos(this.getPosition(0)).below()).getBlock() == RegistryHandler.MAGIC_BLOCK.get()) {
            this.hasPowder = true;
            this.powderRegrowTime = 2400;
            this.recreate();
        }
        powderRegrowTime = Math.max(-POWDER_REGROW_INTERVAL, powderRegrowTime - 1);
        super.tick();
    }
}
