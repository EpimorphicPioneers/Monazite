package com.epimorphismmc.monazite.data;

import com.epimorphismmc.monazite.data.lang.RegistrateCNLangProvider;
import com.tterrag.registrate.providers.ProviderType;

public class MoProviderTypes {
    public static final ProviderType<RegistrateCNLangProvider> CN_LANG = ProviderType.register("cn_lang", (p, e) -> new RegistrateCNLangProvider(p, e.getGenerator().getPackOutput()));
    public static void init() {/**/};
}
