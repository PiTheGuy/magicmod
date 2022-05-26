package com.pitheguy.magicmod.entities;

import com.pitheguy.magicmod.init.ModEntityTypes;
import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class FluffyMagician extends Animal {

    public boolean hasPowder = true;
    public int powderRegrowTime = 2400;

    public FluffyMagician(EntityType<? extends Animal> type, Level worldIn) {
        super(type, worldIn);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob ageable) {
        FluffyMagician entity = new FluffyMagician(ModEntityTypes.FLUFFY_MAGICIAN.get(), this.level);
        entity.finalizeSpawn(world, this.level.getCurrentDifficultyAt(new BlockPos(entity.getPosition(0))), MobSpawnType.BREEDING, null, null);
        return entity;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TemptGoal(this, 1.2, Ingredient.of(RegistryHandler.MAGIC_GEM.get()), false));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0));
    }

    public static AttributeSupplier createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20).add(Attributes.MOVEMENT_SPEED, 0.23).build();
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
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
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    //TODO Test powder regrow
    @Override
    public CompoundTag saveWithoutId(CompoundTag compound) {
        super.saveWithoutId(compound);
        compound.putBoolean("Powder", hasPowder);
        compound.putInt("PowderRegrowCooldown", powderRegrowTime);
        return compound;
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        this.hasPowder = compound.getBoolean("Powder");
        this.powderRegrowTime = compound.getInt("PowderRegrowCooldown");
    }

    public void recreate() {
        if (!this.level.isClientSide && !hasPowder) {
            //LOGGER.info("Converting to Bare Fluffy Magician");
            FluffyMagicianBare newEntity = ModEntityTypes.FLUFFY_MAGICIAN_BARE.get().spawn(this.getServer().overworld(), null, null, null, new BlockPos(this.getPosition(0)), MobSpawnType.CONVERSION, true, true);
            newEntity.restoreFrom(this);
            this.discard();
        }
    }
}
