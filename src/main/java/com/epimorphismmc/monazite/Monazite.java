package com.epimorphismmc.monazite;

import com.epimorphismmc.monazite.api.MonaziteValues;
import com.epimorphismmc.monazite.client.ClientProxy;
import com.epimorphismmc.monazite.common.CommonProxy;
import com.gregtechceu.gtceu.utils.FormattingUtil;
import com.lowdragmc.lowdraglib.LDLib;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(Monazite.MODID)
public class Monazite {

    public static final String MODID = "monazite";

    public static final Logger LOGGER = LogUtils.getLogger();

    public static CommonProxy proxy;

    public Monazite() {
        proxy = DistExecutor.unsafeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MODID, FormattingUtil.toLowerCaseUnder(path));
    }

    public static boolean isJEILoaded() {
        return LDLib.isModLoaded(MonaziteValues.MODID_JEI);
    }

    public static boolean isTOPLoaded() {
        return LDLib.isModLoaded(MonaziteValues.MODID_TOP);
    }

}
