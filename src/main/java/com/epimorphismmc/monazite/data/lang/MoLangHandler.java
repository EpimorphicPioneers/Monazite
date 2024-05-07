package com.epimorphismmc.monazite.data.lang;

import com.tterrag.registrate.providers.RegistrateLangProvider;

public class MoLangHandler {
    public MoLangHandler() {/**/}

    public static void init(RegistrateLangProvider provider) {
        provider.add("monazite.exhaust_vent.blocked", "Blocked");
        provider.add("monazite.exhaust_vent.direction", "Exhaust Vent Direction: %s");
        provider.add("monazite.recipe.output", "Recipe Output:");
        provider.add("monazite.maintenance.broken", "Needs Maintenance");
        provider.add("monazite.maintenance.fixed", "Maintenance Fine");
        provider.add("monazite.auto_output.item", "Item Output Direction: %s");
        provider.add("monazite.auto_output.fluid", "Fluid Output Direction: %s");
        provider.add("monazite.auto_output.auto", "Auto");
        provider.add("monazite.auto_output.allow_input", "Allows Input");
        provider.add("config.jade.plugin_monazite.recipe_output_info", "Recipe Outputs Display");
        provider.add("config.jade.plugin_monazite.maintenance_info", "Maintenance Information Display");
        provider.add("config.jade.plugin_monazite.exhaust_vent_info", "Exhaust Vent Information Display");
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
        provider.add("monazite.exhaust_vent.blocked", "受阻");
        provider.add("monazite.exhaust_vent.direction", "排气口方向: %s");
        provider.add("monazite.recipe.output", "配方输出:");
        provider.add("monazite.maintenance.broken", "需要维护");
        provider.add("monazite.maintenance.fixed", "无需维护");
        provider.add("monazite.auto_output.item", "物品输出方向: %s");
        provider.add("monazite.auto_output.fluid", "流体输出方向: %s");
        provider.add("monazite.auto_output.auto", "自动");
        provider.add("monazite.auto_output.allow_input", "允许输入");
        provider.add("config.jade.plugin_monazite.recipe_output_info", "配方输出显示");
        provider.add("config.jade.plugin_monazite.maintenance_info", "维护信息显示");
        provider.add("config.jade.plugin_monazite.exhaust_vent_info", "排气口信息显示");
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