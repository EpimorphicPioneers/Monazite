package com.epimorphismmc.monazite.config;

import com.epimorphismmc.monazite.Monazite;
import dev.toma.configuration.Configuration;
import dev.toma.configuration.config.Config;
import dev.toma.configuration.config.Configurable;
import dev.toma.configuration.config.format.ConfigFormats;

@Config(id = Monazite.MODID)
public class MonaziteConfigHolder {
    public static MonaziteConfigHolder INSTANCE;

    public static void init() {
        if (INSTANCE == null) {
            INSTANCE = Configuration.registerConfig(MonaziteConfigHolder.class, ConfigFormats.yaml()).getConfigInstance();
        }
    }

    @Configurable
    public TopInformationConfigs topInformation = new TopInformationConfigs();

    public static class TopInformationConfigs {
        @Configurable
        @Configurable.Comment({"If true, the item outputs of the current recipe will be displayed.", "Default: true"})
        public boolean displayItemOutputs = true; // default true

        @Configurable
        @Configurable.Comment({"If true, the name of the item will be displayed.", "Default: true"})
        public boolean displayItemName = true; // default true

        @Configurable
        @Configurable.Comment({"If true, the fluid outputs of the current recipe will be displayed.", "Default: true"})
        public boolean displayFluidOutputs = true; // default true

        @Configurable
        @Configurable.Comment({"If true, the name of the fluid will be displayed.", "Default: true"})
        public boolean displayFluidName = true; // default true

        @Configurable
        @Configurable.Comment({"If true, only the name of item and fluid will be displayed.", "Default: false"})
        public boolean conciseMode = false; // default false

        @Configurable
        @Configurable.Comment({"Each line can have a maximum of several items, which only take effect when displayItemName is false.", "Default: 8"})
        public int itemsPerLine = 8; // default 8
    }

}
