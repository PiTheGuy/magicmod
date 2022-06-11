package com.pitheguy.magicmod.items;

import com.google.common.collect.ImmutableMap;
import com.pitheguy.magicmod.MagicMod;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.*;
import java.util.stream.IntStream;

import static com.pitheguy.magicmod.util.RegistryHandler.*;

public class MagicShelter extends Item {

    public static final Block[] MAGIC_LAMPS = {MAGIC_LAMP_RED.get(), MAGIC_LAMP_ORANGE.get(), MAGIC_LAMP_YELLOW.get(), MAGIC_LAMP_GREEN.get(), MAGIC_LAMP_BLUE.get(), MAGIC_LAMP_PURPLE.get(), MAGIC_LAMP_MAGENTA.get(), MAGIC_LAMP_BLACK.get(), MAGIC_LAMP_WHITE.get()};
    private static final Random RANDOM = new Random();
    public static final Map<String, BlockState> BLOCK_KEY = new HashMap<>(new ImmutableMap.Builder<String, BlockState>()
            .put("O", Blocks.OBSIDIAN.defaultBlockState())
            .put("R", MAGIC_OBSIDIAN.get().defaultBlockState())
            .put("B", Blocks.STONE_BUTTON.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH))
            .put("u", Blocks.IRON_DOOR.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER))
            .put("l", Blocks.IRON_DOOR.defaultBlockState().setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER))
            .put("C", Blocks.CHEST.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH))
            .put("L", Blocks.LADDER.defaultBlockState())
            .put("M", MAGIC_LAMPS[RANDOM.nextInt(MAGIC_LAMPS.length)].defaultBlockState())
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
        super(new Properties().tab(MagicMod.TAB));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        boolean placedBlock = false;
        BlockPos pos = new BlockPos(context.getPlayer().getPosition(0).add(0,-1,0));
        if (!hasEnoughSpace(context.getPlayer(), context.getLevel())) {
            context.getPlayer().displayClientMessage(new TextComponent("Not enough space!").withStyle(ChatFormatting.RED), true);
            return InteractionResult.FAIL;
        }
        if (!hasGroundUnderneath(context.getPlayer(), context.getLevel())) {
            context.getPlayer().displayClientMessage(new TextComponent("Not enough ground to place!").withStyle(ChatFormatting.RED), true);
            return InteractionResult.FAIL;
        }
        for (int x = -2; x <= 2; x++) {
            for (int y = 0; y < STRUCTURE_1.length; y++) {
                for (int z = -2; z <= 2; z++) {
                    String key = STRUCTURE_1[y][z + 2].substring(x + 2, x + 3);
                    if (key.equals("M")) rerollRandomBlocks();
                    BlockState blockState = BLOCK_KEY.get(key);
                    BlockPos placePos = pos.offset(x, y + 1, z);
                    if (blockState != null && context.getLevel().getBlockState(placePos).getMaterial().isReplaceable()) {
                        context.getLevel().setBlock(placePos, blockState, 0);
                        if (key.equals("C")) {
                            LevelChunk chunk = context.getLevel().getChunkAt(placePos);
                            RandomizableContainerBlockEntity.setLootTable(context.getLevel().getChunkForCollisions(chunk.getPos().x, chunk.getPos().z), new Random(), placePos, new ResourceLocation("magicmod", "chests/magic_shelter_chest"));
                        }
                        placedBlock = true;
                    }
                }
            }
        }
        if (placedBlock) {
            context.getLevel().playSound(context.getPlayer(), pos.getX(), pos.getY(), pos.getZ(), SoundEvents.STONE_PLACE, SoundSource.BLOCKS, 1, context.getLevel().getRandom().nextFloat() * 0.1f + 0.9f);
            if (!context.getPlayer().getAbilities().instabuild) {
                context.getPlayer().getItemInHand(context.getHand()).shrink(1);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private boolean hasGroundUnderneath(Player player, Level world) {
        return IntStream.rangeClosed(-1, 1).noneMatch(x -> IntStream.rangeClosed(-1, 1).anyMatch(z -> world.getBlockState(new BlockPos(player.getPosition(0).add(x, -2, z))).isAir()));
    }

    private boolean hasEnoughSpace(Player player, Level world) {
        for (int x = -2; x <= 2; x++) {
            for (int y = 0; y < STRUCTURE_1.length; y++) {
                for (int z = -2; z <= 2; z++) {
                    if (!world.getBlockState(new BlockPos(player.getPosition(0).add(x, y, z))).getMaterial().isReplaceable()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void rerollRandomBlocks() {
        BLOCK_KEY.replace("M", MAGIC_LAMPS[RANDOM.nextInt(MAGIC_LAMPS.length)].defaultBlockState());
    }

}
