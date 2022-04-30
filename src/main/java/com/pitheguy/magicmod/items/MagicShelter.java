package com.pitheguy.magicmod.items;

import com.google.common.collect.ImmutableMap;
import com.pitheguy.magicmod.MagicMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import static com.pitheguy.magicmod.util.RegistryHandler.*;

public class MagicShelter extends Item {

    public static final Block[] MAGIC_LAMPS = {MAGIC_LAMP_RED.get(), MAGIC_LAMP_ORANGE.get(), MAGIC_LAMP_YELLOW.get(), MAGIC_LAMP_GREEN.get(), MAGIC_LAMP_BLUE.get(), MAGIC_LAMP_PURPLE.get(), MAGIC_LAMP_MAGENTA.get(), MAGIC_LAMP_BLACK.get(), MAGIC_LAMP_WHITE.get()};
    private static final Random RANDOM = new Random();
    public static final Map<String, BlockState> BLOCK_KEY = new HashMap<>(new ImmutableMap.Builder<String, BlockState>()
            .put("O", Blocks.OBSIDIAN.getDefaultState())
            .put("R", MAGIC_OBSIDIAN.get().getDefaultState())
            .put("B", Blocks.STONE_BUTTON.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH))
            .put("u", Blocks.IRON_DOOR.getDefaultState().with(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER))
            .put("l", Blocks.IRON_DOOR.getDefaultState().with(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER))
            .put("C", Blocks.CHEST.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH))
            .put("L", Blocks.LADDER.getDefaultState())
            .put("M", MAGIC_LAMPS[RANDOM.nextInt(MAGIC_LAMPS.length)].getDefaultState())
            .build());

    public static String[][] STRUCTURE_1 = {
            {"ORuRO", "O   O", "O   O", "OL  O", "OOOOO"}, //Layer 1
            {"ORlRO", "O  BO", "O   O", "OL  O", "OOOOO"}, //Layer 2
            {"ORRRO", "O   O", "O   O", "OL  O", "OOOOO"}, //Layer 3
            {"OOOOO", "OOOOO", "OOOOO", "OLOOO", "OOOOO"}, //Layer 4
            {"OOOOO", "O C O", "O   O", "O   O", "OOOOO"}, //Layer 5
            {"OOOOO", "O   O", "O   O", "O   O", "OOOOO"}, //Layer 6
            {"OOOOO", "O   O", "O   O", "O   O", "OOOOO"}, //Layer 7
            {"OOOOO", "OOOOO", "OOMOO", "OOOOO", "OOOOO"}}; //Layer 8

    public MagicShelter() {
        super(new Item.Properties().group(MagicMod.TAB));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        boolean placedBlock = false;
        BlockPos pos = context.getPlayer().getPosition().add(0,-1,0);
        if (!hasEnoughSpace(context.getPlayer(), context.getWorld())) {
            context.getPlayer().sendStatusMessage(new StringTextComponent("Not enough space!").applyTextStyle(TextFormatting.RED), true);
            return ActionResultType.FAIL;
        }
        if (!hasGroundUnderneath(context.getPlayer(), context.getWorld())) {
            context.getPlayer().sendStatusMessage(new StringTextComponent("Not enough ground to place!").applyTextStyle(TextFormatting.RED), true);
            return ActionResultType.FAIL;
        }
        for (int x = -2; x <= 2; x++) {
            for (int y = 0; y < STRUCTURE_1.length; y++) {
                for (int z = -2; z <= 2; z++) {
                    String key = STRUCTURE_1[y][z + 2].substring(x + 2, x + 3);
                    if (key.equals("M")) rerollRandomBlocks();
                    BlockState blockState = BLOCK_KEY.get(key);
                    BlockPos placePos = pos.add(x, y + 1, z);
                    if (blockState != null && context.getWorld().getBlockState(placePos).isAir()) {
                        context.getWorld().setBlockState(placePos, blockState);
                        if (key.equals("C")) {
                            Chunk chunk = context.getWorld().getChunkAt(placePos);
                            LockableLootTileEntity.setLootTable(context.getWorld().getBlockReader(chunk.getPos().x, chunk.getPos().z), new Random(), placePos, new ResourceLocation("magicmod", "chests/magic_shelter_chest"));
                        }
                        placedBlock = true;
                    }
                }
            }
        }
        if (placedBlock) {
            context.getWorld().playSound(context.getPlayer(), pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 1, context.getWorld().rand.nextFloat() * 0.1f + 0.9f);
            if (!context.getPlayer().abilities.isCreativeMode) {
                context.getPlayer().getHeldItem(context.getHand()).shrink(1);
            }
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    private boolean hasGroundUnderneath(PlayerEntity player, World world) {
        return IntStream.rangeClosed(-1, 1).noneMatch(x -> IntStream.rangeClosed(-1, 1).anyMatch(z -> world.getBlockState(player.getPosition().add(x, -2, z)).isAir()));
    }

    private boolean hasEnoughSpace(PlayerEntity player, World world) {
        for (int x = -2; x <= 2; x++) {
            for (int y = 0; y < STRUCTURE_1.length; y++) {
                for (int z = -2; z <= 2; z++) {
                    if (!world.getBlockState(player.getPosition().add(x, y, z)).isAir()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void rerollRandomBlocks() {
        BLOCK_KEY.replace("M", MAGIC_LAMPS[RANDOM.nextInt(MAGIC_LAMPS.length)].getDefaultState());
    }

}
