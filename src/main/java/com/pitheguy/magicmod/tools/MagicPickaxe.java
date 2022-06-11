package com.pitheguy.magicmod.tools;

import com.pitheguy.magicmod.util.RegistryHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class MagicPickaxe extends PickaxeItem {
    public MagicPickaxe(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        int blocksBroken = 1;
        if (entityLiving instanceof Player player) {
            BlockGetter reader = player.level.getChunkForCollisions(player.level.getChunkAt(pos).getPos().x, player.level.getChunkAt(pos).getPos().z);
            double d = player.getDigSpeed(state, pos) >= 1 ? 0.02 : 0.1;
            if (Math.random() < EnchantmentHelper.getEnchantmentLevel(RegistryHandler.MAGIC_FINDER.get(), player) * d) {
                player.level.addFreshEntity(new ItemEntity(player.level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(RegistryHandler.MAGIC_POWDER.get())));
            }
            int veinminerLevel = EnchantmentHelper.getEnchantmentLevel(RegistryHandler.VEINMINER.get(), player);
            if (veinminerLevel > 0 && state.canHarvestBlock(reader, pos, player)) {
                for (int x = -veinminerLevel; x <= veinminerLevel; x++) {
                    for (int y = -veinminerLevel; y <= veinminerLevel; y++) {
                        for (int z = -veinminerLevel; z <= veinminerLevel; z++) {
                            BlockPos currBlock = pos.offset(x, y, z);
                            if (worldIn.getBlockState(currBlock).getBlock() == state.getBlock()) {
                                worldIn.destroyBlock(currBlock, true, player);
                                blocksBroken++;
                            }
                        }
                    }
                }
            }
        }
        if (!worldIn.isClientSide && state.getDestroySpeed(worldIn, pos) != 0.0F) {
           stack.hurtAndBreak(blocksBroken, entityLiving, entity -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }

        return true;
    }
}
