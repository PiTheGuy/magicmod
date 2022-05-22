package com.pitheguy.magicmod.items;

import com.pitheguy.magicmod.MagicMod;
import com.pitheguy.magicmod.init.ModEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Objects;

public class PopularityOrb extends Item {
    public PopularityOrb() {
        super(new Item.Properties().tab(MagicMod.TAB));
    }

    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        if (!world.isClientSide) {
            ItemStack itemstack = context.getItemInHand();
            BlockPos blockpos = context.getClickedPos();
            Direction direction = context.getClickedFace();
            BlockState blockstate = world.getBlockState(blockpos);
            BlockPos blockpos1 = blockstate.getCollisionShape(world, blockpos).isEmpty() ? blockpos : blockpos.relative(direction);
            if (ModEntityTypes.MAGIC_FRIEND.get().spawn(world.getServer().overworld(), itemstack, context.getPlayer(), blockpos1, SpawnReason.SPAWN_EGG, true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP) != null) {
                itemstack.shrink(1);
            }

        }
        return ActionResultType.SUCCESS;
    }

    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        BlockRayTraceResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, RayTraceContext.FluidMode.SOURCE_ONLY);
        if (raytraceresult.getType() != RayTraceResult.Type.BLOCK) {
            return ActionResult.pass(itemstack);
        } else if (worldIn.isClientSide) {
            return ActionResult.success(itemstack);
        } else {
            BlockPos blockpos = raytraceresult.getBlockPos();
            if (!(worldIn.getBlockState(blockpos).getBlock() instanceof FlowingFluidBlock)) {
                return ActionResult.pass(itemstack);
            } else if (worldIn.mayInteract(playerIn, blockpos) && playerIn.mayUseItemAt(blockpos, raytraceresult.getDirection(), itemstack)) {
                if (ModEntityTypes.MAGIC_FRIEND.get().spawn(worldIn.getServer().overworld(), itemstack, playerIn, blockpos, SpawnReason.SPAWN_EGG, false, false) == null) {
                    return ActionResult.pass(itemstack);
                } else {
                    if (!playerIn.abilities.instabuild) {
                        itemstack.shrink(1);
                    }
                    playerIn.awardStat(Stats.ITEM_USED.get(this));
                    return ActionResult.success(itemstack);
                }
            } else {
                return ActionResult.fail(itemstack);
            }
        }
    }
}
