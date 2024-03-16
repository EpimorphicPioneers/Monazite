package com.epimorphismmc.monazite.client;

import com.epimorphismmc.monazite.common.CommonProxy;
import com.epimorphismmc.monazite.keys.KeyBindings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy {
    public ClientProxy() {
        super();
    }

    @SubscribeEvent
    public void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        KeyBindings.init();
        event.register(KeyBindings.toggleConciseMode);
    }
}
