package com.epimorphismmc.monazite.keys;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.settings.KeyConflictContext;

@OnlyIn(Dist.CLIENT)
public class KeyBindings {
    public static KeyMapping toggleConciseMode;

    public KeyBindings() {/**/}

    public static void init() {
        toggleConciseMode = new KeyMapping("key.toggleConciseMode", KeyConflictContext.IN_GAME, InputConstants.UNKNOWN, "key.categories.monazite");
    }
}
