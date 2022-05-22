package com.pitheguy.magicmod.entities;

import com.pitheguy.magicmod.init.ModEntityTypes;
import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.jetbrains.annotations.Nullable;

public class MagicFriend extends AnimalEntity {

    public MagicFriend(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity ageable) {
        MagicFriend entity = new MagicFriend(ModEntityTypes.MAGIC_FRIEND.get(), this.level);
        entity.finalizeSpawn(world, this.level.getCurrentDifficultyAt(new BlockPos(entity.getPosition(0))), SpawnReason.BREEDING, null, null);
        return entity;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new TemptGoal(this, 1.2, Ingredient.of(RegistryHandler.MAGIC_CARROT.get()), false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1.0));
    }

    public static AttributeModifierMap createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 20).add(Attributes.MOVEMENT_SPEED, 0.23).build();
    }

    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    public void travel(Vector3d vector) {
        if (this.isAlive()) {
            Entity entity = this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
            if (this.isVehicle() && this.canBeControlledByRider()) {
                this.yRot = entity.yRot;
                this.yRotO = this.yRot;
                this.xRot = entity.xRot * 0.5F;
                this.setRot(this.yRot, this.xRot);
                this.yBodyRot = this.yRot;
                this.yHeadRot = this.yRot;
                this.maxUpStep = 1.0F;
                this.flyingSpeed = this.getSpeed() * 0.1F;

                if (this.isControlledByLocalInstance()) {
                    this.setSpeed((float) this.getAttribute(Attributes.MOVEMENT_SPEED).getValue() * 1.5f);
                    super.travel(new Vector3d(0.0D, 0.0D, 1.0D));
                    this.lerpSteps = 0;
                } else {
                    this.setDeltaMovement(Vector3d.ZERO);
                }

                this.animationSpeedOld = this.animationSpeed;
                double d1 = this.getX() - this.xo;
                double d0 = this.getZ() - this.zo;
                float f1 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
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

    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        if (super.mobInteract(player, hand) == ActionResultType.SUCCESS) {
            return ActionResultType.SUCCESS;
        } else {
            ItemStack itemstack = player.getItemInHand(hand);
            if (itemstack.getItem() == Items.NAME_TAG) {
                itemstack.interactLivingEntity(player, this, hand);
                return ActionResultType.SUCCESS;
            } else if (!this.isVehicle()) {
                if (!this.level.isClientSide) {
                    player.startRiding(this);
                }
                return ActionResultType.SUCCESS;
            } else {
                return ActionResultType.PASS;
            }
        }
    }

    public boolean canBeControlledByRider() {
        Entity entity = this.getControllingPassenger();
        if (entity instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)entity;
            return playerentity.getMainHandItem().getItem() == RegistryHandler.MAGIC_CARROT.get() || playerentity.getOffhandItem().getItem() == RegistryHandler.MAGIC_CARROT.get();
        } else return false;
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
        return MathHelper.ceil((distance * 0.15F - 7.0F) * damageMultiplier);
    }
}
