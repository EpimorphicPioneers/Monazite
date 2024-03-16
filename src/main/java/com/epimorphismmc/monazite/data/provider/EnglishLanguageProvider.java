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
        add("monazite.item.outputs", "Item Crafting:");
        add("monazite.fluid.outputs", "Fluid Crafting:");
        add("config.jade.plugin_monazite.recipe_info_item_output", "Recipe Items Display");
        add("config.jade.plugin_monazite.recipe_info_fluid_output", "Recipe Fluids Display");
    }
}
