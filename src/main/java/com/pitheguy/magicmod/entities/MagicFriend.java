package com.pitheguy.magicmod.entities;

import com.pitheguy.magicmod.init.ModEntityTypes;
import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class MagicFriend extends Animal {

    public MagicFriend(EntityType<? extends Animal> type, Level worldIn) {
        super(type, worldIn);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob ageable) {
        MagicFriend entity = new MagicFriend(ModEntityTypes.MAGIC_FRIEND.get(), this.level);
        entity.finalizeSpawn(world, this.level.getCurrentDifficultyAt(new BlockPos(entity.getPosition(0))), MobSpawnType.BREEDING, null, null);
        return entity;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TemptGoal(this, 1.2, Ingredient.of(RegistryHandler.MAGIC_CARROT.get()), false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
    }

    public static AttributeSupplier createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20).add(Attributes.MOVEMENT_SPEED, 0.23).build();
    }

    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    public void travel(Vec3 vector) {
        if (this.isAlive()) {
            Entity entity = this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
            if (this.isVehicle() && this.canBeControlledByRider()) {
                this.setYRot(entity.getYRot());
                this.yRotO = this.getYRot();
                this.setXRot(entity.getXRot() * 0.5F);
                this.setRot(this.getYRot(), this.getXRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.getYRot();
                this.maxUpStep = 1.0F;
                this.flyingSpeed = this.getSpeed() * 0.1F;

                if (this.isControlledByLocalInstance()) {
                    this.setSpeed((float) this.getAttribute(Attributes.MOVEMENT_SPEED).getValue() * 1.5f);
                    super.travel(new Vec3(0.0D, 0.0D, 1.0D));
                    this.lerpSteps = 0;
                } else {
                    this.setDeltaMovement(Vec3.ZERO);
                }

                this.animationSpeedOld = this.animationSpeed;
                double d1 = this.getX() - this.xo;
                double d0 = this.getZ() - this.zo;
                float f1 = Mth.sqrt((float) (d1 * d1 + d0 * d0)) * 4.0F;
                f1 = Math.min(f1, 1.0F);

                this.animationSpeed += (f1 - this.animationSpeed) * 0.4F;
                this.animationPosition += this.animationSpeed;
            } else {
                this.maxUpStep = 0.5F;
                this.flyingSpeed = 0.02F;
                super.travel(vector);
            }
        }
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (super.mobInteract(player, hand) == InteractionResult.SUCCESS) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemstack = player.getItemInHand(hand);
            if (itemstack.getItem() == Items.NAME_TAG) {
                itemstack.interactLivingEntity(player, this, hand);
                return InteractionResult.SUCCESS;
            } else if (!this.isVehicle()) {
                if (!this.level.isClientSide) {
                    player.startRiding(this);
                }
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    public boolean canBeControlledByRider() {
        Entity entity = this.getControllingPassenger();
        return entity instanceof Player player && (player.getMainHandItem().getItem() == RegistryHandler.MAGIC_CARROT.get() || player.getOffhandItem().getItem() == RegistryHandler.MAGIC_CARROT.get());
    }

    public boolean causeFallDamage(float distance, float damageMultiplier) {
        if (distance > 1.0F) {
            this.playSound(SoundEvents.HORSE_LAND, 0.4F, 1.0F);
        }

        int i = this.calculateFallDamage(distance, damageMultiplier);
        if (i <= 0) {
            return false;
        } else {
            this.hurt(DamageSource.FALL, i);
            if (this.isVehicle()) {
                for(Entity entity : this.getIndirectPassengers()) {
                    entity.hurt(DamageSource.FALL, i);
                }
            }

            this.playBlockFallSound();
            return true;
        }
    }

    protected int calculateFallDamage(float distance, float damageMultiplier) {
        return Mth.ceil((distance * 0.15F - 7.0F) * damageMultiplier);
    }
}
