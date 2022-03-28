package com.pitheguy.magicmod.util;

import com.pitheguy.magicmod.MagicMod;
import com.pitheguy.magicmod.armor.CustomArmorMagic;
import com.pitheguy.magicmod.armor.MagicArmorMaterial;
import com.pitheguy.magicmod.armor.ReinforcedMagicArmorMaterial;
import com.pitheguy.magicmod.blocks.*;
import com.pitheguy.magicmod.items.ItemBase;
import com.pitheguy.magicmod.tools.MagicHoe;
import com.pitheguy.magicmod.tools.MagicItemTier;
import com.pitheguy.magicmod.tools.ReinforcedMagicHoe;
import com.pitheguy.magicmod.tools.ReinforcedMagicItemTier;
import net.minecraft.block.Block;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, MagicMod.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, MagicMod.MOD_ID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    //Items
    public static final RegistryObject<Item> MAGIC_GEM = ITEMS.register("magic_gem", ItemBase::new);
    public static final RegistryObject<Item> MAGIC_CORE = ITEMS.register("magic_core", ItemBase::new);
    public static final RegistryObject<Item> NETHER_SHARD = ITEMS.register("nether_shard", ItemBase::new);
    public static final RegistryObject<Item> MAGIC_PLATE = ITEMS.register("magic_plate", ItemBase::new);

    //Magic Dust
    public static final RegistryObject<Item> MAGIC_DUST_RED = ITEMS.register("magic_dust_red", ItemBase::new);
    public static final RegistryObject<Item> MAGIC_DUST_ORANGE = ITEMS.register("magic_dust_orange", ItemBase::new);
    public static final RegistryObject<Item> MAGIC_DUST_YELLOW = ITEMS.register("magic_dust_yellow", ItemBase::new);
    public static final RegistryObject<Item> MAGIC_DUST_GREEN = ITEMS.register("magic_dust_green", ItemBase::new);
    public static final RegistryObject<Item> MAGIC_DUST_BLUE = ITEMS.register("magic_dust_blue", ItemBase::new);
    public static final RegistryObject<Item> MAGIC_DUST_PURPLE = ITEMS.register("magic_dust_purple", ItemBase::new);
    public static final RegistryObject<Item> MAGIC_DUST_MAGENTA = ITEMS.register("magic_dust_magenta", ItemBase::new);
    public static final RegistryObject<Item> MAGIC_DUST_BLACK = ITEMS.register("magic_dust_black", ItemBase::new);
    public static final RegistryObject<Item> MAGIC_DUST_WHITE = ITEMS.register("magic_dust_white", ItemBase::new);

    //Magic Orbs
    public static final RegistryObject<Item> MAGIC_ORB_RED = ITEMS.register("magic_orb_red", ItemBase::new);
    public static final RegistryObject<Item> MAGIC_ORB_ORANGE = ITEMS.register("magic_orb_orange", ItemBase::new);
    public static final RegistryObject<Item> MAGIC_ORB_YELLOW = ITEMS.register("magic_orb_yellow", ItemBase::new);
    public static final RegistryObject<Item> MAGIC_ORB_GREEN = ITEMS.register("magic_orb_green", ItemBase::new);
    public static final RegistryObject<Item> MAGIC_ORB_BLUE = ITEMS.register("magic_orb_blue", ItemBase::new);
    public static final RegistryObject<Item> MAGIC_ORB_PURPLE = ITEMS.register("magic_orb_purple", ItemBase::new);
    public static final RegistryObject<Item> MAGIC_ORB_MAGENTA = ITEMS.register("magic_orb_magenta", ItemBase::new);
    public static final RegistryObject<Item> MAGIC_ORB_BLACK = ITEMS.register("magic_orb_black", ItemBase::new);
    public static final RegistryObject<Item> MAGIC_ORB_WHITE = ITEMS.register("magic_orb_white", ItemBase::new);

    //Magic Tools
    public static final RegistryObject<SwordItem> MAGIC_SWORD = ITEMS.register("magic_sword", () ->
            new SwordItem(MagicItemTier.MAGIC, 70, 0.0f, new Item.Properties().group(MagicMod.TAB)));
    public static final RegistryObject<PickaxeItem> MAGIC_PICKAXE = ITEMS.register("magic_pickaxe", () ->
            new PickaxeItem(MagicItemTier.MAGIC, 50, 0.0f, new Item.Properties().group(MagicMod.TAB)));
    public static final RegistryObject<AxeItem> MAGIC_AXE = ITEMS.register("magic_axe", () ->
            new AxeItem(MagicItemTier.MAGIC, 80, -1.0f, new Item.Properties().group(MagicMod.TAB)));
    public static final RegistryObject<ShovelItem> MAGIC_SHOVEL = ITEMS.register("magic_shovel", () ->
            new ShovelItem(MagicItemTier.MAGIC, 55, 0.0f, new Item.Properties().group(MagicMod.TAB)));
    public static final RegistryObject<HoeItem> MAGIC_HOE = ITEMS.register("magic_hoe", () ->
            new MagicHoe(MagicItemTier.MAGIC, 0.0f, new Item.Properties().group(MagicMod.TAB)));

    //Reinforced Magic Tools
    public static final RegistryObject<SwordItem> REINFORCED_MAGIC_SWORD = ITEMS.register("reinforced_magic_sword", () ->
            new SwordItem(ReinforcedMagicItemTier.REINFORCED_MAGIC, 200, 0.0f, new Item.Properties().group(MagicMod.TAB)));
    public static final RegistryObject<PickaxeItem> REINFORCED_MAGIC_PICKAXE = ITEMS.register("reinforced_magic_pickaxe", () ->
            new PickaxeItem(ReinforcedMagicItemTier.REINFORCED_MAGIC, 150, 0.0f, new Item.Properties().group(MagicMod.TAB)));
    public static final RegistryObject<AxeItem> REINFORCED_MAGIC_AXE = ITEMS.register("reinforced_magic_axe", () ->
            new AxeItem(ReinforcedMagicItemTier.REINFORCED_MAGIC, 225, -1.0f, new Item.Properties().group(MagicMod.TAB)));
    public static final RegistryObject<ShovelItem> REINFORCED_MAGIC_SHOVEL = ITEMS.register("reinforced_magic_shovel", () ->
            new ShovelItem(ReinforcedMagicItemTier.REINFORCED_MAGIC, 160, 0.0f, new Item.Properties().group(MagicMod.TAB)));
    public static final RegistryObject<HoeItem> REINFORCED_MAGIC_HOE = ITEMS.register("reinforced_magic_hoe", () ->
            new ReinforcedMagicHoe(ReinforcedMagicItemTier.REINFORCED_MAGIC, 0.0f, new Item.Properties().group(MagicMod.TAB)));

    //Magic Armor
    public static final RegistryObject<ArmorItem> MAGIC_HELMET = ITEMS.register("magic_helmet", () ->
            new CustomArmorMagic(MagicArmorMaterial.MAGIC, EquipmentSlotType.HEAD, new Item.Properties().group(MagicMod.TAB)));
    public static final RegistryObject<ArmorItem> MAGIC_CHESTPLATE = ITEMS.register("magic_chestplate", () ->
            new CustomArmorMagic(MagicArmorMaterial.MAGIC, EquipmentSlotType.CHEST, new Item.Properties().group(MagicMod.TAB)));
    public static final RegistryObject<ArmorItem> MAGIC_LEGGINGS = ITEMS.register("magic_leggings", () ->
            new CustomArmorMagic(MagicArmorMaterial.MAGIC, EquipmentSlotType.LEGS, new Item.Properties().group(MagicMod.TAB)));
    public static final RegistryObject<ArmorItem> MAGIC_BOOTS = ITEMS.register("magic_boots", () ->
            new CustomArmorMagic(MagicArmorMaterial.MAGIC, EquipmentSlotType.FEET, new Item.Properties().group(MagicMod.TAB)));

    //Reinforced Magic Armor
    public static final RegistryObject<ArmorItem> REINFORCED_MAGIC_HELMET = ITEMS.register("reinforced_magic_helmet", () ->
            new CustomArmorMagic(ReinforcedMagicArmorMaterial.REINFORCED_MAGIC, EquipmentSlotType.HEAD, new Item.Properties().group(MagicMod.TAB)));
    public static final RegistryObject<ArmorItem> REINFORCED_MAGIC_CHESTPLATE = ITEMS.register("reinforced_magic_chestplate", () ->
            new CustomArmorMagic(ReinforcedMagicArmorMaterial.REINFORCED_MAGIC, EquipmentSlotType.CHEST, new Item.Properties().group(MagicMod.TAB)));
    public static final RegistryObject<ArmorItem> REINFORCED_MAGIC_LEGGINGS = ITEMS.register("reinforced_magic_leggings", () ->
            new CustomArmorMagic(ReinforcedMagicArmorMaterial.REINFORCED_MAGIC, EquipmentSlotType.LEGS, new Item.Properties().group(MagicMod.TAB)));
    public static final RegistryObject<ArmorItem> REINFORCED_MAGIC_BOOTS = ITEMS.register("reinforced_magic_boots", () ->
            new CustomArmorMagic(ReinforcedMagicArmorMaterial.REINFORCED_MAGIC, EquipmentSlotType.FEET, new Item.Properties().group(MagicMod.TAB)));

    //Blocks
    public static final RegistryObject<Block> MAGIC_ORE = BLOCKS.register("magic_ore", MagicOre::new);
    public static final RegistryObject<Block> MAGIC_OBSIDIAN = BLOCKS.register("magic_obsidian", MagicObsidian::new);
    public static final RegistryObject<Block> MAGIC_BLOCK = BLOCKS.register("magic_block", MagicBlock::new);
    public static final RegistryObject<Block> MAGIC_SLAB = BLOCKS.register("magic_slab", MagicSlab::new);
    public static final RegistryObject<Block> MAGIC_LAMP_RED = BLOCKS.register("magic_lamp_red", MagicLamp::new);
    public static final RegistryObject<Block> MAGIC_LAMP_ORANGE = BLOCKS.register("magic_lamp_orange", MagicLamp::new);
    public static final RegistryObject<Block> MAGIC_LAMP_YELLOW = BLOCKS.register("magic_lamp_yellow", MagicLamp::new);
    public static final RegistryObject<Block> MAGIC_LAMP_GREEN = BLOCKS.register("magic_lamp_green", MagicLamp::new);
    public static final RegistryObject<Block> MAGIC_LAMP_BLUE = BLOCKS.register("magic_lamp_blue", MagicLamp::new);
    public static final RegistryObject<Block> MAGIC_LAMP_PURPLE = BLOCKS.register("magic_lamp_purple", MagicLamp::new);
    public static final RegistryObject<Block> MAGIC_LAMP_MAGENTA = BLOCKS.register("magic_lamp_magenta", MagicLamp::new);
    public static final RegistryObject<Block> MAGIC_LAMP_BLACK = BLOCKS.register("magic_lamp_black", MagicLamp::new);
    public static final RegistryObject<Block> MAGIC_LAMP_WHITE = BLOCKS.register("magic_lamp_white", MagicLamp::new);


    //Block Items
    public static final RegistryObject<Item> MAGIC_ORE_ITEM = ITEMS.register("magic_ore", () -> new BlockItemBase(MAGIC_ORE.get()));
    public static final RegistryObject<Item> MAGIC_OBSIDIAN_ITEM = ITEMS.register("magic_obsidian", () -> new BlockItemBase(MAGIC_OBSIDIAN.get()));
    public static final RegistryObject<Item> MAGIC_BLOCK_ITEM = ITEMS.register("magic_block", () -> new BlockItemBase(MAGIC_BLOCK.get()));
    public static final RegistryObject<Item> MAGIC_SLAB_ITEM = ITEMS.register("magic_slab", () -> new BlockItemBase(MAGIC_SLAB.get()));
    public static final RegistryObject<Item> MAGIC_LAMP_RED_ITEM = ITEMS.register("magic_lamp_red", () -> new BlockItemBase(MAGIC_LAMP_RED.get()));
    public static final RegistryObject<Item> MAGIC_LAMP_ORANGE_ITEM = ITEMS.register("magic_lamp_orange", () -> new BlockItemBase(MAGIC_LAMP_ORANGE.get()));
    public static final RegistryObject<Item> MAGIC_LAMP_YELLOW_ITEM = ITEMS.register("magic_lamp_yellow", () -> new BlockItemBase(MAGIC_LAMP_YELLOW.get()));
    public static final RegistryObject<Item> MAGIC_LAMP_GREEN_ITEM = ITEMS.register("magic_lamp_green", () -> new BlockItemBase(MAGIC_LAMP_GREEN.get()));
    public static final RegistryObject<Item> MAGIC_LAMP_BLUE_ITEM = ITEMS.register("magic_lamp_blue", () -> new BlockItemBase(MAGIC_LAMP_BLUE.get()));
    public static final RegistryObject<Item> MAGIC_LAMP_PURPLE_ITEM = ITEMS.register("magic_lamp_purple", () -> new BlockItemBase(MAGIC_LAMP_PURPLE.get()));
    public static final RegistryObject<Item> MAGIC_LAMP_MAGENTA_ITEM = ITEMS.register("magic_lamp_magenta", () -> new BlockItemBase(MAGIC_LAMP_MAGENTA.get()));
    public static final RegistryObject<Item> MAGIC_LAMP_BLACK_ITEM = ITEMS.register("magic_lamp_black", () -> new BlockItemBase(MAGIC_LAMP_BLACK.get()));
    public static final RegistryObject<Item> MAGIC_LAMP_WHITE_ITEM = ITEMS.register("magic_lamp_white", () -> new BlockItemBase(MAGIC_LAMP_WHITE.get()));
}