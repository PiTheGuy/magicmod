package com.pitheguy.magicmod.entities;

import com.pitheguy.magicmod.init.ModEntityTypes;
import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.jetbrains.annotations.Nullable;

public class FluffyMagician extends AnimalEntity {

    public boolean hasPowder = true;
    public int powderRegrowTime = 2400;

    public FluffyMagician(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity ageable) {
        FluffyMagician entity = new FluffyMagician(ModEntityTypes.FLUFFY_MAGICIAN.get(), this.level);
        entity.finalizeSpawn(world, this.level.getCurrentDifficultyAt(new BlockPos(entity.getPosition(0))), SpawnReason.BREEDING, null, null);
        return entity;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new TemptGoal(this, 1.2, Ingredient.of(RegistryHandler.MAGIC_GEM.get()), false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, 1.0));
    }

    public static AttributeModifierMap createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 20).add(Attributes.MOVEMENT_SPEED, 0.23).build();
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getItem() == RegistryHandler.MAGIC_SHEARS.get() && this.hasPowder) {
            if (!this.level.isClientSide) {
                this.hasPowder = false;
                this.powderRegrowTime = 2400;
                int i = 1 + this.getRandom().nextInt(1);

                for(int j = 0; j < i; ++j) {
                    ItemEntity itementity = this.spawnAtLocation(RegistryHandler.MAGIC_POWDER.get(), 1);
                    if (itementity != null) {
                        itementity.setDeltaMovement(itementity.getDeltaMovement().add((this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.1F, this.getRandom().nextFloat() * 0.05F, (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.1F));
                    }
                }
                itemstack.hurtAndBreak(1, player, (p_213613_1_) -> p_213613_1_.broadcastBreakEvent(hand));
            }
            this.playSound(SoundEvents.SHEEP_SHEAR, 1.0F, 1.0F);
            this.recreate();
            return ActionResultType.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    //TODO Test powder regrow
    @Override
    public CompoundNBT saveWithoutId(CompoundNBT compound) {
        super.saveWithoutId(compound);
        compound.putBoolean("Powder", hasPowder);
        compound.putInt("PowderRegrowCooldown", powderRegrowTime);
        return compound;
    }

    @Override
    public void load(CompoundNBT compound) {
        super.load(compound);
        this.hasPowder = compound.getBoolean("Powder");
        this.powderRegrowTime = compound.getInt("PowderRegrowCooldown");
    }

    public void recreate() {
        if (!this.level.isClientSide && !hasPowder) {
            //LOGGER.info("Converting to Bare Fluffy Magician");
            FluffyMagicianBare newEntity = ModEntityTypes.FLUFFY_MAGICIAN_BARE.get().spawn(this.getServer().overworld(), null, null, null, new BlockPos(this.getPosition(0)), SpawnReason.CONVERSION, true, true);
            newEntity.restoreFrom(this);
            this.remove();
        }
    }
}
