package com.epimorphismmc.monazite.data.lang;

import com.tterrag.registrate.providers.RegistrateLangProvider;

public class MoLangHandler {
    public MoLangHandler() {/**/}

    public static void init(RegistrateLangProvider provider) {
        provider.add("monazite.recipe.output", "Recipe Output:");
        provider.add("config.jade.plugin_monazite.recipe_info_output", "Recipe Outputs Display");
        provider.add("key.categories.monazite", "Monazite");
        provider.add("key.toggleConciseMode", "Toggle Concise Mode");
        provider.add("config.monazite.option.topInformation", "Top Information");
        provider.add("config.monazite.option.displayRecipeOutputs", "Recipe Outputs Display");
        provider.add("config.monazite.option.conciseMode", "Concise Mode");
        provider.add("config.monazite.option.itemsPerLine", "Items Per Line");
        provider.add("block.monazite.dimension_display.overworld", "Overworld");
        provider.add("block.monazite.dimension_display.the_nether", "The Nether");
        provider.add("block.monazite.dimension_display.the_end", "The End");
    }

    public static void init(RegistrateCNLangProvider provider) {
        provider.add("monazite.recipe.output", "配方输出:");
        provider.add("config.jade.plugin_monazite.recipe_info_output", "配方输出显示");
        provider.add("key.categories.monazite", "Monazite");
        provider.add("key.toggleConciseMode", "开/关简洁模式");
        provider.add("config.monazite.option.topInformation", "顶部信息");
        provider.add("config.monazite.option.displayRecipeOutputs", "配方输出显示");
        provider.add("config.monazite.option.conciseMode", "简洁模式");
        provider.add("config.monazite.option.itemsPerLine", "每行物品数");
        provider.add("block.monazite.dimension_display.overworld", "主世界");
        provider.add("block.monazite.dimension_display.the_nether", "下界");
        provider.add("block.monazite.dimension_display.the_end", "末地");
    }
}
