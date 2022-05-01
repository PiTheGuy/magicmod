package com.pitheguy.magicmod.entities;

import com.pitheguy.magicmod.init.ModEntityTypes;
import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.World;

public class FluffyMagicianBare extends FluffyMagician {

    public static final int POWDER_REGROW_INTERVAL = 4800;

    public FluffyMagicianBare(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
        this.hasPowder = false;
        this.powderRegrowTime = 2400;
    }

    @Override
    public void recreate() {
        if (!this.world.isRemote && hasPowder) {
            //LOGGER.info("Converting to regular Fluffy Magician");
            FluffyMagician newEntity = ModEntityTypes.FLUFFY_MAGICIAN.get().spawn(this.world, null, null, null, this.getPosition(), SpawnReason.CONVERSION, true, true);
            newEntity.copyDataFromOld(this);
            this.remove();
        }
    }

    @Override
    public void livingTick() {
        if (powderRegrowTime <= -POWDER_REGROW_INTERVAL && this.world.getBlockState(this.getPosition().down()).getBlock() == RegistryHandler.MAGIC_BLOCK.get()) {
            this.hasPowder = true;
            this.recreate();
        }
        powderRegrowTime = Math.max(-POWDER_REGROW_INTERVAL, powderRegrowTime - 1);
        super.livingTick();
    }
}
