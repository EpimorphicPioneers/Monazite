package com.epimorphismmc.monazite;

import com.epimorphismmc.monazite.common.registry.MoRegistrate;
import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;

@GTAddon
public class MoGTAddon implements IGTAddon {

    @Override
    public void initializeAddon() {
        Monazite.proxy.init();
    }

    @Override
    public GTRegistrate getRegistrate() {
        return MoRegistrate.MO_REGISTRATE;
    }

    @Override
    public String addonModId() {
        return Monazite.MODID;
    }

}
