package com.pitheguy.magicmod.entities;

import com.pitheguy.magicmod.init.ModEntityTypes;
import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FluffyMagician extends AnimalEntity {

    public boolean hasPowder = true;
    public int powderRegrowTime = 0;

    public FluffyMagician(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Nullable
    @Override
    public AgeableEntity createChild(AgeableEntity ageable) {
        FluffyMagician entity = new FluffyMagician(ModEntityTypes.FLUFFY_MAGICIAN.get(), this.world);
        entity.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(entity)), SpawnReason.BREEDING, null, null);
        return entity;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new TemptGoal(this, 1.2, Ingredient.fromItems(RegistryHandler.MAGIC_GEM.get()), false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1.0));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23);
    }

    @Override
    public void livingTick() {
        if (powderRegrowTime == 0 && !hasPowder && this.world.getBlockState(this.getPosition().down()).getBlock() == RegistryHandler.MAGIC_BLOCK.get()) {
            this.hasPowder = true;
            this.recreate();
        }
        powderRegrowTime = Math.max(0, powderRegrowTime - 1);
        super.livingTick();
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        if (itemstack.getItem() == Items.SHEARS && this.hasPowder) { //TODO: Add custom shears for this
            if (!this.world.isRemote) {
                this.hasPowder = false;
                this.powderRegrowTime = 2400;
                int i = 1 + this.rand.nextInt(1);

                for(int j = 0; j < i; ++j) {
                    ItemEntity itementity = this.entityDropItem(RegistryHandler.MAGIC_POWDER.get(), 1);
                    if (itementity != null) {
                        itementity.setMotion(itementity.getMotion().add((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F, this.rand.nextFloat() * 0.05F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F));
                    }
                }
                itemstack.damageItem(1, player, (p_213613_1_) -> p_213613_1_.sendBreakAnimation(hand));
            }
            this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
            return true;
        }
        return super.processInteract(player, hand);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("Powder", hasPowder);
        compound.putInt("PowderRegrowCooldown", powderRegrowTime);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.hasPowder = compound.getBoolean("Powder");
        this.powderRegrowTime = compound.getInt("PowderRegrowTime");
    }

    public void recreate() {
        if (!this.world.isRemote) {
            FluffyMagician newEntity = new FluffyMagician(ModEntityTypes.FLUFFY_MAGICIAN.get(), this.world);
            this.world.addEntity(newEntity);
            newEntity.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
            newEntity.copyDataFromOld(this);
            this.remove();
        }
    }
}
