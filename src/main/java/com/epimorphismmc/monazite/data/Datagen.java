package com.epimorphismmc.monazite.data;

import com.epimorphismmc.monazite.data.provider.ChineseLanguageProvider;
import com.epimorphismmc.monazite.data.provider.EnglishLanguageProvider;
import net.minecraftforge.data.event.GatherDataEvent;

public class Datagen {
    public static void init(GatherDataEvent event) {
        var gen = event.getGenerator();
        var packOutput = gen.getPackOutput();
        var helper = event.getExistingFileHelper();
        gen.addProvider(event.includeClient(), new EnglishLanguageProvider(packOutput));
        gen.addProvider(event.includeClient(), new ChineseLanguageProvider(packOutput));
    }
}
