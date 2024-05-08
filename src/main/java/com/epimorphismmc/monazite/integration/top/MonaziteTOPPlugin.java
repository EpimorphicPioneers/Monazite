package com.epimorphismmc.monazite.integration.top;

import com.epimorphismmc.monazite.Monazite;
import com.epimorphismmc.monazite.config.MonaziteConfigHolder;
import com.epimorphismmc.monazite.integration.top.element.FluidStackElement;
import com.epimorphismmc.monazite.integration.top.provider.*;
import com.gregtechceu.gtceu.config.ConfigHolder;
import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.IElement;
import mcjty.theoneprobe.api.IElementFactory;
import mcjty.theoneprobe.api.ITheOneProbe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class MonaziteTOPPlugin {

    public static final ResourceLocation ELEMENT_FLUID = Monazite.id("fluid");

    public static void init() {
        ITheOneProbe oneProbe = TheOneProbe.theOneProbeImp;
        registerElements(oneProbe);
        registerProvider(oneProbe);
    }

    public static void registerProvider(ITheOneProbe oneProbe) {
        if (MonaziteConfigHolder.INSTANCE.topInformation.displayRecipeOutputs) {
            oneProbe.registerProvider(new RecipeOutputInfoProvider());
        }

        if (ConfigHolder.INSTANCE.machines.enableMaintenance && MonaziteConfigHolder.INSTANCE.topInformation.displayMaintenanceInfo) {
            oneProbe.registerProvider(new MaintenanceInfoProvider());
        }

        if (MonaziteConfigHolder.INSTANCE.topInformation.displayExhaustVentInfo) {
            oneProbe.registerProvider(new ExhaustVentInfoProvider());
        }

        if (MonaziteConfigHolder.INSTANCE.topInformation.displayAutoOutputInfo) {
            oneProbe.registerProvider(new AutoOutputInfoProvider());
        }

        if (MonaziteConfigHolder.INSTANCE.topInformation.displayCableInfo) {
            oneProbe.registerProvider(new CableInfoProvider());
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
