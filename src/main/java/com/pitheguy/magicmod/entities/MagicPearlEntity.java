package com.pitheguy.magicmod.entities;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.EntityTeleportEvent;

public class MagicPearlEntity extends ThrownEnderpearl {

    public MagicPearlEntity(EntityType<? extends ThrownEnderpearl> p_i50153_1_, Level p_i50153_2_) {
        super(p_i50153_1_, p_i50153_2_);
    }

    public MagicPearlEntity(Level worldIn, LivingEntity throwerIn) {
        super(worldIn, throwerIn);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        Entity entity = this.getOwner();

        for(int i = 0; i < 32; ++i) {
            this.level.addParticle(ParticleTypes.PORTAL, this.getX(), this.getY() + this.random.nextDouble() * 2.0D, this.getZ(), this.random.nextGaussian(), 0.0D, this.random.nextGaussian());
        }

        if (!this.level.isClientSide && !this.isRemoved()) {
            if (entity instanceof ServerPlayer serverPlayer) {
                if (serverPlayer.connection.getConnection().isConnected() && serverPlayer.level == this.level && !serverPlayer.isSleeping()) {
                    EntityTeleportEvent.EnderPearl event = ForgeEventFactory.onEnderPearlLand(serverPlayer, this.getX(), this.getY(), this.getZ(), this, 1.0F);
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

            this.discard();
        }

    }
}
