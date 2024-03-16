package com.epimorphismmc.monazite.common;

import com.epimorphismmc.monazite.Monazite;
import com.epimorphismmc.monazite.config.MonaziteConfigHolder;
import com.epimorphismmc.monazite.data.Datagen;
import com.epimorphismmc.monazite.integration.top.MonaziteTOPPlugin;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class CommonProxy {

    public CommonProxy() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.register(this);
        eventBus.addListener(Datagen::init);
        MonaziteConfigHolder.init();
    }

    @SubscribeEvent
    public void loadComplete(FMLLoadCompleteEvent event) {
        event.enqueueWork(() -> {
            if (Monazite.isTOPLoaded()) {
                Monazite.LOGGER.info("TheOneProbe found. Enabling integration...");
                MonaziteTOPPlugin.init();
            }
        });
    }
}
