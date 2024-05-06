package com.epimorphismmc.monazite.data;

import com.epimorphismmc.monazite.common.registry.MoRegistrate;
import com.epimorphismmc.monazite.data.lang.MoLangHandler;
import com.tterrag.registrate.providers.ProviderType;

public class MoDatagen {
    public static void init() {
        MoRegistrate.MO_REGISTRATE.addDataGenerator(ProviderType.LANG, MoLangHandler::init);
        MoRegistrate.MO_REGISTRATE.addDataGenerator(MoProviderTypes.CN_LANG, MoLangHandler::init);
    }
}
