package com.epimorphismmc.monazite.data.provider;

import com.epimorphismmc.monazite.Monazite;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class EnglishLanguageProvider extends LanguageProvider {

    public EnglishLanguageProvider(PackOutput packOutput) {
        super(packOutput, Monazite.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("monazite.jade.output", "Recipe Output:");
        add("config.jade.plugin_monazite.recipe_info_output", "Recipe Outputs Display");
        add("key.categories.monazite", "Monazite");
        add("key.toggleConciseMode", "Toggle Concise Mode");
        add("config.monazite.option.topInformation", "Top Information");
        add("config.monazite.option.displayItemOutputs", "Display Item Outputs");
        add("config.monazite.option.displayItemName", "Display Item Name");
        add("config.monazite.option.displayFluidOutputs", "Display Fluid Outputs");
        add("config.monazite.option.displayFluidName", "Display Fluid Name");
        add("config.monazite.option.conciseMode", "Concise Mode");
        add("config.monazite.option.itemsPerLine", "Items Per Line");
    }
}
