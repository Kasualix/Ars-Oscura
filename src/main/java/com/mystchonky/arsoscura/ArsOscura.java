package com.mystchonky.arsoscura;

import com.hollingsworth.arsnouveau.setup.ClientProxy;
import com.hollingsworth.arsnouveau.setup.IProxy;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import com.hollingsworth.arsnouveau.setup.ServerProxy;
import com.mystchonky.arsoscura.common.config.BaseConfig;
import com.mystchonky.arsoscura.common.init.ArsNouveauIntegration;
import com.mystchonky.arsoscura.common.init.ArsOscuraItems;
import com.mystchonky.arsoscura.common.init.ArsOscuraLang;
import com.mystchonky.arsoscura.common.integration.bloodmagic.BloodMagicIntegration;
import com.mystchonky.arsoscura.common.integration.occultism.OccultismIntegration;
import com.mystchonky.arsoscura.common.network.Networking;
import com.tterrag.registrate.Registrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ArsOscura.MODID)
public class ArsOscura {
    public static final String MODID = "ars_oscura";
    public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new); //TODO: Change to our own

    private static final Lazy<Registrate> REGISTRATE = Lazy.of(() -> Registrate.create(MODID));
    public static final Logger LOGGER = LogManager.getLogger();

    public static CreativeModeTab ArsOscuraTab = new CreativeModeTab(CreativeModeTab.getGroupCountSafe(), MODID) {
        @Override
        public ItemStack makeIcon() {
            return ItemsRegistry.WAND.get().getDefaultInstance();
        }
    };

    public static Registrate registrate() {
        return REGISTRATE.get();
    }

    public ArsOscura() {
        // Ensure the config subdirectory is present.
        try {
            Files.createDirectories(FMLPaths.CONFIGDIR.get().resolve(MODID));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Register config files
        var ctx = ModLoadingContext.get();
        ctx.registerConfig(ModConfig.Type.COMMON, BaseConfig.COMMON_SPEC, MODID + "/base-common.toml");
        ctx.registerConfig(ModConfig.Type.CLIENT, BaseConfig.CLIENT_SPEC, MODID + "/base-client.toml");

        ArsOscuraItems.register();
        ArsNouveauIntegration.init();

        if (ModList.get().isLoaded("bloodmagic"))
            BloodMagicIntegration.init();
        if (ModList.get().isLoaded("occultism"))
            OccultismIntegration.init();

        ArsOscuraLang.register();

        IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
        modbus.addListener(this::setup);
        modbus.addListener(this::doClientStuff);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation prefix(String path) {
        return new ResourceLocation(MODID, path);
    }

    private void setup(final FMLCommonSetupEvent event) {
        ArsNouveauIntegration.postInit();
        Networking.registerMessages();
        if (ModList.get().isLoaded("occultism"))
            OccultismIntegration.postInit();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

}
