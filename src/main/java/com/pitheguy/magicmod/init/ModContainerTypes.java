package com.pitheguy.magicmod.init;

import com.pitheguy.magicmod.container.*;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.*;

public class ModContainerTypes {
    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPES = DeferredRegister.create(
            ForgeRegistries.CONTAINERS, "magicmod");
    public static final RegistryObject<MenuType<MagicInfuserContainer>> MAGIC_INFUSER = CONTAINER_TYPES
            .register("magic_infuser", () -> IForgeMenuType.create(MagicInfuserContainer::new));
    public static final RegistryObject<MenuType<MagicCrateContainer>> MAGIC_CRATE = CONTAINER_TYPES
            .register("magic_crate", () -> IForgeMenuType.create(MagicCrateContainer::new));
    public static final RegistryObject<MenuType<MagicPressContainer>> MAGIC_PRESS = CONTAINER_TYPES
            .register("magic_press", () -> IForgeMenuType.create(MagicPressContainer::new));
    public static final RegistryObject<MenuType<MagicEnergizerContainer>> MAGIC_ENERGIZER = CONTAINER_TYPES
            .register("magic_energizer", () -> IForgeMenuType.create(MagicEnergizerContainer::new));
    public static final RegistryObject<MenuType<MagicMinerContainer>> MAGIC_MINER = CONTAINER_TYPES
            .register("magic_miner", () -> IForgeMenuType.create(MagicMinerContainer::new));
    public static final RegistryObject<MenuType<MagicLoggerContainer>> MAGIC_LOGGER = CONTAINER_TYPES
            .register("magic_logger", () -> IForgeMenuType.create(MagicLoggerContainer::new));
}
