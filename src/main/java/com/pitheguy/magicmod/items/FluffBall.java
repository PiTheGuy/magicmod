package com.pitheguy.magicmod.items;

import com.pitheguy.magicmod.MagicMod;
import com.pitheguy.magicmod.init.ModEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.Objects;

public class FluffBall extends Item {
    public FluffBall() {
        super(new Properties().tab(MagicMod.TAB));
    }

    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!level.isClientSide) {
            ItemStack itemstack = context.getItemInHand();
            BlockPos blockpos = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState blockstate = level.getBlockState(blockpos);
            BlockPos blockpos1 = blockstate.getCollisionShape(level, blockpos).isEmpty() ? blockpos : blockpos.relative(direction);
            if (ModEntityTypes.FLUFFY_MAGICIAN.get().spawn(level.getServer().overworld(), itemstack, context.getPlayer(), blockpos1, MobSpawnType.SPAWN_EGG, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP) != null) {
                itemstack.shrink(1);
            }

        }
        return InteractionResult.SUCCESS;
    }

    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        BlockHitResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.SOURCE_ONLY);
        if (raytraceresult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(itemstack);
        } else if (worldIn.isClientSide) {
            return InteractionResultHolder.success(itemstack);
        } else {
            BlockPos blockpos = raytraceresult.getBlockPos();
            if (!(worldIn.getBlockState(blockpos).getBlock() instanceof LiquidBlock)) {
                return InteractionResultHolder.pass(itemstack);
            } else if (worldIn.mayInteract(playerIn, blockpos) && playerIn.mayUseItemAt(blockpos, raytraceresult.getDirection(), itemstack)) {
                if (ModEntityTypes.FLUFFY_MAGICIAN.get().spawn(worldIn.getServer().overworld(), itemstack, playerIn, blockpos, MobSpawnType.SPAWN_EGG, false, false) == null) {
                    return InteractionResultHolder.pass(itemstack);
                } else {
                    if (!playerIn.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }
                    playerIn.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.success(itemstack);
                }
            } else {
                return InteractionResultHolder.fail(itemstack);
            }
        }
    }
}
