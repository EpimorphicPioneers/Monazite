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
        add("monazite.recipe.output", "配方输出:");
        add("config.jade.plugin_monazite.recipe_info_output", "配方输出显示");
        add("key.categories.monazite", "Monazite");
        add("key.toggleConciseMode", "开/关简洁模式");
        add("config.monazite.option.topInformation", "顶部信息");
        add("config.monazite.option.conciseMode", "简洁模式");
        add("config.monazite.option.itemsPerLine", "每行物品数");
    }
}
