package com.mystchonky.arsoscura.datagen;

import com.mystchonky.arsoscura.ArsOscura;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ArsOscura.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Setup {

    //use runData configuration to generate stuff, event.includeServer() for data, event.includeClient() for assets
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();

//        gen.addProvider(event.includeServer(), new ArsProviders.ImbuementProvider(gen));
//        gen.addProvider(event.includeServer(), new ArsProviders.GlyphProvider(gen));
//        gen.addProvider(event.includeServer(), new ArsProviders.EnchantingAppProvider(gen));

        gen.addProvider(event.includeServer(), new BloodMagicProviders.AltarProvider(gen));
        gen.addProvider(event.includeServer(), new BloodMagicProviders.AlchemyTableProvider(gen));
        gen.addProvider(event.includeServer(), new BloodMagicProviders.GlyphProvider(gen));

        gen.addProvider(event.includeServer(), new ArsProviders.PatchouliProvider(gen));

        gen.addProvider(event.includeClient(), new GlyphItemModelProvider(gen, event.getExistingFileHelper()));
    }

}
