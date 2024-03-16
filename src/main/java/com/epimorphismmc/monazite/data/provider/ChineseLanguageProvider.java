package com.epimorphismmc.monazite.data.provider;

import com.epimorphismmc.monazite.Monazite;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class ChineseLanguageProvider extends LanguageProvider {

    public ChineseLanguageProvider(PackOutput packOutput) {
        super(packOutput, Monazite.MODID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        add("monazite.item.outputs", "物品制作中:");
        add("monazite.fluid.outputs", "流体制作中:");
        add("config.jade.plugin_monazite.recipe_info_item_output", "配方物品显示");
        add("config.jade.plugin_monazite.recipe_info_fluid_output", "配方流体显示");
    }
}
