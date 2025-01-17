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
        provider.add("monazite.cable.voltage", "Voltage: ");
        provider.add("monazite.cable.amperage", "Amperage: ");
        provider.add("monazite.machine_mode", "Machine Mode: ");
        provider.add("monazite.stained", "Stained: %s");
        provider.add("config.jade.plugin_monazite.recipe_output_info", "Recipe Outputs Display");
        provider.add("config.jade.plugin_monazite.maintenance_info", "Maintenance Information Display");
        provider.add("config.jade.plugin_monazite.exhaust_vent_info", "Exhaust Vent Information Display");
        provider.add("config.jade.plugin_monazite.auto_output_info", "Auto Output Information Display");
        provider.add("config.jade.plugin_monazite.cable_info", "Cable Information Display");
        provider.add("config.jade.plugin_monazite.machine_mode", "Machine Mode Display");
        provider.add("config.jade.plugin_monazite.multiblock_structure", "Multiblock Structure Display");
        provider.add("config.jade.plugin_monazite.stained_color", "Stained Color Display");
        provider.add("config.jade.plugin_monazite.parallel", "Parallel Display");
        provider.add("key.categories.monazite", "Monazite");
        provider.add("key.toggleConciseMode", "Toggle Concise Mode");
        provider.add("config.screen.monazite", "Monazite");
        provider.add("config.monazite.option.topInformation", "Top Information");
        provider.add("config.monazite.option.displayRecipeOutputs", "Recipe Outputs Display");
        provider.add("config.monazite.option.conciseMode", "Concise Mode");
        provider.add("config.monazite.option.itemsPerLine", "Items Per Line");
        provider.add("config.monazite.option.displayMaintenanceInfo", "Maintenance Information Display");
        provider.add("config.monazite.option.displayExhaustVentInfo", "Exhaust Vent Information Display");
        provider.add("config.monazite.option.displayAutoOutputInfo", "Auto Output Information Display");
        provider.add("config.monazite.option.displayCableInfo", "Cable Information Display");
        provider.add("config.monazite.option.useNHDimensionDisplay", "Using NH-style Dimension Display");
        provider.add("config.monazite.option.showDimensionTier", "Showing Dimension Tier");
        provider.add("config.monazite.option.dimensions", "Custom Dimensions");
        provider.add("config.monazite.option.displayMachineMode",  "Display Machine Mode");
        provider.add("config.monazite.option.displayMulitblockStructure", "Display Mulitblock Structure");
        provider.add("config.monazite.option.displayStainedColor",  "Display Stained Color");
        provider.add("config.monazite.option.displayParallel", "Display Parallel");
        provider.add("block.monazite.dimension_display.minecraft.overworld", "Overworld");
        provider.add("block.monazite.dimension_display.minecraft.the_nether", "The Nether");
        provider.add("block.monazite.dimension_display.minecraft.the_end", "The End");
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
        provider.add("monazite.cable.voltage", "电压: ");
        provider.add("monazite.cable.amperage", "电流: ");
        provider.add("monazite.machine_mode", "机器模式: ");
        provider.add("monazite.stained", "已染色: %s");
        provider.add("config.jade.plugin_monazite.recipe_output_info", "配方输出显示");
        provider.add("config.jade.plugin_monazite.maintenance_info", "维护信息显示");
        provider.add("config.jade.plugin_monazite.exhaust_vent_info", "排气口信息显示");
        provider.add("config.jade.plugin_monazite.auto_output_info", "自动输出信息显示");
        provider.add("config.jade.plugin_monazite.cable_info", "线缆信息显示");
        provider.add("config.jade.plugin_monazite.machine_mode", "机器模式显示");
        provider.add("config.jade.plugin_monazite.multiblock_structure", "多方块结构显示");
        provider.add("config.jade.plugin_monazite.stained_color", "染色方块显示");
        provider.add("config.jade.plugin_monazite.parallel", "并行显示");
        provider.add("key.categories.monazite", "Monazite");
        provider.add("key.toggleConciseMode", "开/关简洁模式");
        provider.add("config.screen.monazite", "Monazite");
        provider.add("config.monazite.option.topInformation", "顶部信息");
        provider.add("config.monazite.option.oreVeinDisplay", "矿脉显示");
        provider.add("config.monazite.option.displayRecipeOutputs", "配方输出显示");
        provider.add("config.monazite.option.conciseMode", "简洁模式");
        provider.add("config.monazite.option.itemsPerLine", "每行物品数");
        provider.add("config.monazite.option.displayMaintenanceInfo", "维护信息显示");
        provider.add("config.monazite.option.displayExhaustVentInfo", "排气口信息显示");
        provider.add("config.monazite.option.displayAutoOutputInfo", "自动输出信息显示");
        provider.add("config.monazite.option.displayCableInfo", "线缆信息显示");
        provider.add("config.monazite.option.useNHDimensionDisplay", "使用NH样式的维度显示");
        provider.add("config.monazite.option.showDimensionTier", "显示维度层级");
        provider.add("config.monazite.option.dimensions", "自定义维度");
        provider.add("config.monazite.option.displayMachineMode",  "显示机器模式");
        provider.add("config.monazite.option.displayMulitblockStructure", "显示多方块结构");
        provider.add("config.monazite.option.displayStainedColor",  "显示已染色方块");
        provider.add("config.monazite.option.displayParallel", "显示并行");
        provider.add("block.monazite.dimension_display.minecraft.overworld", "主世界");
        provider.add("block.monazite.dimension_display.minecraft.the_nether", "下界");
        provider.add("block.monazite.dimension_display.minecraft.the_end", "末地");
    }
}
