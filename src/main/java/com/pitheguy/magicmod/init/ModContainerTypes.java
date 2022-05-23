package com.pitheguy.magicmod.init;

import com.pitheguy.magicmod.blocks.MagicLogger;
import com.pitheguy.magicmod.container.*;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerTypes {
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(
            ForgeRegistries.CONTAINERS, "magicmod");
    public static final RegistryObject<ContainerType<MagicInfuserContainer>> MAGIC_INFUSER = CONTAINER_TYPES
            .register("magic_infuser", () -> IForgeContainerType.create(MagicInfuserContainer::new));
    public static final RegistryObject<ContainerType<MagicCrateContainer>> MAGIC_CRATE = CONTAINER_TYPES
            .register("magic_crate", () -> IForgeContainerType.create(MagicCrateContainer::new));
    public static final RegistryObject<ContainerType<MagicPressContainer>> MAGIC_PRESS = CONTAINER_TYPES
            .register("magic_press", () -> IForgeContainerType.create(MagicPressContainer::new));
    public static final RegistryObject<ContainerType<MagicEnergizerContainer>> MAGIC_ENERGIZER = CONTAINER_TYPES
            .register("magic_energizer", () -> IForgeContainerType.create(MagicEnergizerContainer::new));
    public static final RegistryObject<ContainerType<MagicMinerContainer>> MAGIC_MINER = CONTAINER_TYPES
            .register("magic_miner", () -> IForgeContainerType.create(MagicMinerContainer::new));
    public static final RegistryObject<ContainerType<MagicLoggerContainer>> MAGIC_LOGGER = CONTAINER_TYPES
            .register("magic_logger", () -> IForgeContainerType.create(MagicLoggerContainer::new));
}
