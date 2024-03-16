package com.epimorphismmc.monazite.integration.top;

import com.epimorphismmc.monazite.Monazite;
import com.epimorphismmc.monazite.config.MonaziteConfigHolder;
import com.epimorphismmc.monazite.integration.top.element.FluidStackElement;
import com.epimorphismmc.monazite.integration.top.provider.RecipeFluidOutputInfoProvider;
import com.epimorphismmc.monazite.integration.top.provider.RecipeItemOutputInfoProvider;
import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.IElement;
import mcjty.theoneprobe.api.IElementFactory;
import mcjty.theoneprobe.api.ITheOneProbe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class MonaziteTOPPlugin {

    public static final ResourceLocation ELEMENT_FLUID = Monazite.id("fluid");
    public static final ResourceLocation ELEMENT_FLUID_GAUGE = Monazite.id("fluid_gauge");

    public static void init() {
        ITheOneProbe oneProbe = TheOneProbe.theOneProbeImp;
        registerElements(oneProbe);
        registerProvider(oneProbe);
    }

    public static void registerProvider(ITheOneProbe oneProbe) {
        if (MonaziteConfigHolder.INSTANCE.topInformation.displayItemOutputs) {
            oneProbe.registerProvider(new RecipeItemOutputInfoProvider());
        }

        if (MonaziteConfigHolder.INSTANCE.topInformation.displayFluidOutputs) {
            oneProbe.registerProvider(new RecipeFluidOutputInfoProvider());
        }
    }

    public static void registerElements(ITheOneProbe oneProbe) {
        oneProbe.registerElementFactory(create(ELEMENT_FLUID, FluidStackElement::new));
    }


    private static IElementFactory create(final ResourceLocation id, final Function<FriendlyByteBuf, IElement> factory) {
        return new IElementFactory() {
            public IElement createElement(FriendlyByteBuf buf) {
                return factory.apply(buf);
            }

            public ResourceLocation getId() {
                return id;
            }
        };
    }
}
