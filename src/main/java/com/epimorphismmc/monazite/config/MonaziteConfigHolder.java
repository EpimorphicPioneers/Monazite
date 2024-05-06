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

    @Configurable
    public OreVeinDisplayConfigs oreVeinDisplay = new OreVeinDisplayConfigs();

    public static class TopInformationConfigs {

        @Configurable
        @Configurable.Comment({"If true, the outputs of the current recipe will be displayed.", "Default: true"})
        public boolean displayRecipeOutputs = true; // default true

        @Configurable
        @Configurable.Comment({"If true, only the name of item and fluid will be displayed.", "Default: false"})
        public boolean conciseMode = false; // default false

        @Configurable
        @Configurable.Comment({"Each line can have a maximum of several items, which only take effect when displayItemName is false.", "Default: 8"})
        public int itemsPerLine = 8; // default 8

        @Configurable
        @Configurable.Comment({"If true, the maintenance information of the current machine will be displayed.", "Default: true"})
        public boolean displayMaintenanceInfo = true; // default true
    }

    public static class OreVeinDisplayConfigs {
        @Configurable
        @Configurable.Comment("To add custom display dimensions.")
        public String[] dimensions = new String[0];
    }

}
