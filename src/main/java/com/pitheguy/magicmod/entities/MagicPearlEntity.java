package com.pitheguy.magicmod.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.EntityTeleportEvent;

public class MagicPearlEntity extends EnderPearlEntity {

    public MagicPearlEntity(EntityType<? extends EnderPearlEntity> p_i50153_1_, World p_i50153_2_) {
        super(p_i50153_1_, p_i50153_2_);
    }

    public MagicPearlEntity(World worldIn, LivingEntity throwerIn) {
        super(worldIn, throwerIn);
    }

    @Override
    protected void onHit(RayTraceResult result) {
        super.onHit(result);
        Entity entity = this.getOwner();

        for(int i = 0; i < 32; ++i) {
            this.level.addParticle(ParticleTypes.PORTAL, this.getX(), this.getY() + this.random.nextDouble() * 2.0D, this.getZ(), this.random.nextGaussian(), 0.0D, this.random.nextGaussian());
        }

        if (!this.level.isClientSide && !this.removed) {
            if (entity instanceof ServerPlayerEntity) {
                ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)entity;
                if (serverplayerentity.connection.getConnection().isConnected() && serverplayerentity.level == this.level && !serverplayerentity.isSleeping()) {
                    EntityTeleportEvent.EnderPearl event = ForgeEventFactory.onEnderPearlLand(serverplayerentity, this.getX(), this.getY(), this.getZ(), this, 1.0F);
                    if (!event.isCanceled()) {
                        if (entity.isPassenger()) {
                            entity.stopRiding();
                        }
                        entity.teleportTo(event.getTargetX(), event.getTargetY(), event.getTargetZ());
                        entity.fallDistance = 0.0F;
                        entity.hurt(DamageSource.FALL, event.getAttackDamage());
                    }
                }
            } else if (entity != null) {
                entity.teleportTo(this.getX(), this.getY(), this.getZ());
                entity.fallDistance = 0.0F;
            }

            this.remove();
        }

    }
}
