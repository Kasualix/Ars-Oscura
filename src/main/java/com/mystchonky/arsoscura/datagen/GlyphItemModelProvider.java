package com.mystchonky.arsoscura.datagen;

import com.mystchonky.arsoscura.ArsOscura;
import com.mystchonky.arsoscura.common.init.ArsNouveauIntegration;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class GlyphItemModelProvider extends ItemModelProvider {
    public GlyphItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ArsOscura.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ArsNouveauIntegration.registeredSpells.forEach(spell -> basicItem(spell.getRegistryName()));
    }
}
