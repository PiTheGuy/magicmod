package com.pitheguy.magicmod.entities;

import com.pitheguy.magicmod.init.ModEntityTypes;
import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MagicFriend extends AnimalEntity {

    public MagicFriend(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        MagicFriend entity = new MagicFriend(ModEntityTypes.MAGIC_FRIEND.get(), this.world);
        entity.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(entity)), SpawnReason.BREEDING, null, null);
        return entity;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new TemptGoal(this, 1.2, Ingredient.fromItems(RegistryHandler.MAGIC_CARROT.get()), false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1.0));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23);
    }

    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    public void travel(Vec3d vector) {
        if (this.isAlive()) {
            Entity entity = this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
            if (this.isBeingRidden() && this.canBeSteered()) {
                this.rotationYaw = entity.rotationYaw;
                this.prevRotationYaw = this.rotationYaw;
                this.rotationPitch = entity.rotationPitch * 0.5F;
                this.setRotation(this.rotationYaw, this.rotationPitch);
                this.renderYawOffset = this.rotationYaw;
                this.rotationYawHead = this.rotationYaw;
                this.stepHeight = 1.0F;
                this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;

                if (this.canPassengerSteer()) {
                    this.setAIMoveSpeed((float) this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue() * 1.5f);
                    super.travel(new Vec3d(0.0D, 0.0D, 1.0D));
                    this.newPosRotationIncrements = 0;
                } else {
                    this.setMotion(Vec3d.ZERO);
                }

                this.prevLimbSwingAmount = this.limbSwingAmount;
                double d1 = this.getPosX() - this.prevPosX;
                double d0 = this.getPosZ() - this.prevPosZ;
                float f1 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
                if (f1 > 1.0F) {
                    f1 = 1.0F;
                }

                this.limbSwingAmount += (f1 - this.limbSwingAmount) * 0.4F;
                this.limbSwing += this.limbSwingAmount;
            } else {
                this.stepHeight = 0.5F;
                this.jumpMovementFactor = 0.02F;
                super.travel(vector);
            }
        }
    }

    public boolean processInteract(PlayerEntity player, Hand hand) {
        if (super.processInteract(player, hand)) {
            return true;
        } else {
            ItemStack itemstack = player.getHeldItem(hand);
            if (itemstack.getItem() == Items.NAME_TAG) {
                itemstack.interactWithEntity(player, this, hand);
                return true;
            } else if (!this.isBeingRidden()) {
                if (!this.world.isRemote) {
                    player.startRiding(this);
                }

                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean canBeSteered() {
        Entity entity = this.getControllingPassenger();
        if (entity instanceof PlayerEntity) {
            PlayerEntity playerentity = (PlayerEntity)entity;
            return playerentity.getHeldItemMainhand().getItem() == RegistryHandler.MAGIC_CARROT.get() || playerentity.getHeldItemOffhand().getItem() == RegistryHandler.MAGIC_CARROT.get();
        } else return false;
    }
}
