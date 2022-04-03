package com.pitheguy.magicmod;

import com.pitheguy.magicmod.client.gui.MagicInfuserScreen;
import com.pitheguy.magicmod.init.ModContainerTypes;
import com.pitheguy.magicmod.init.ModTileEntityTypes;
import com.pitheguy.magicmod.util.RegistryHandler;
import com.pitheguy.magicmod.world.gen.ModOreGen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("magicmod")
@Mod.EventBusSubscriber(modid = "magicmod", bus = EventBusSubscriber.Bus.MOD)
public class MagicMod
{
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "magicmod";

    public MagicMod() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        RegistryHandler.init();

        ModTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        ModContainerTypes.CONTAINER_TYPES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

    }

    private void setup(final FMLCommonSetupEvent event)
    {

    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        ScreenManager.registerFactory(ModContainerTypes.MAGIC_INFUSER.get(), MagicInfuserScreen::new);
    }

    @SubscribeEvent
    public static void loadCompleteEvent(FMLLoadCompleteEvent event) {
        ModOreGen.generateOre();
    }

    public static final ItemGroup TAB = new ItemGroup("magicTab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(RegistryHandler.MAGIC_GEM.get());
        }
    };

}
