package com.pitheguy.magicmod.tools;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class MagicHoe extends HoeItem {
    private final int abilityRange;
    public MagicHoe(Tier tier, int attackDamage, float attackSpeedIn, Properties builder, int abilityRange) {
        super(tier, attackDamage, attackSpeedIn, builder);
        this.abilityRange = abilityRange;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        int hook = ForgeEventFactory.onHoeUse(context);
        if (hook != 0) return hook > 0 ? InteractionResult.SUCCESS : InteractionResult.FAIL;
        Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> pair = TILLABLES.get(level.getBlockState(blockpos).getBlock());
        if (context.getClickedFace() != Direction.DOWN && level.isEmptyBlock(blockpos.above())) {
            if (pair == null) return InteractionResult.PASS;
            int blocksTilled = 0;
            for (int tillX = -abilityRange; tillX <= abilityRange; tillX++) {
                for (int tillZ = -abilityRange; tillZ <= abilityRange; tillZ++) {
                    BlockPos tillpos = blockpos.offset(tillX,0,tillZ);
                    pair = TILLABLES.get(level.getBlockState(tillpos).getBlock());
                    if (pair != null) {
                        Predicate<UseOnContext> predicate = pair.getFirst();
                        Consumer<UseOnContext> consumer = pair.getSecond();
                        if (predicate.test(context)) {
                            Player player = context.getPlayer();
                            level.playSound(player, tillpos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                            if (!level.isClientSide) {
                                consumer.accept(context);
                                blocksTilled++;
                                if (player != null && blocksTilled == 1) {
                                    context.getItemInHand().hurtAndBreak(1, player, (p_220043_1_) -> p_220043_1_.broadcastBreakEvent(context.getHand()));
                                }
                            }
                        }
                    }
                }
            }
            if (blocksTilled > 0) {
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    private static final List<Block> CROP_BLOCKS_AGE_7 = Arrays.asList(Blocks.WHEAT, Blocks.CARROTS, Blocks.POTATOES);
    private static final List<Block> CROP_BLOCKS_AGE_3 = Arrays.asList(Blocks.BEETROOTS, Blocks.NETHER_WART);

    @Override
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockState currBlock = worldIn.getBlockState(pos.offset(x, 0, z));
                if((CROP_BLOCKS_AGE_7.contains(currBlock.getBlock()) && currBlock.getValue(BlockStateProperties.AGE_7) == 7) || (CROP_BLOCKS_AGE_3.contains(currBlock.getBlock()) && currBlock.getValue(BlockStateProperties.AGE_3) == 3)) {
                    worldIn.destroyBlock(pos.offset(x, 0, z), true);
                }
            }
        }
        return super.mineBlock(stack, worldIn, state, pos, entityLiving);
    }
}
