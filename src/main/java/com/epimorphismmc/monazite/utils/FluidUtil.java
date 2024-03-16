package com.epimorphismmc.monazite.utils;

import com.lowdragmc.lowdraglib.side.fluid.FluidHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

public class FluidUtil {
    public static FluidStack getFluidStack(com.lowdragmc.lowdraglib.side.fluid.FluidStack fluidStack) {
        return (FluidStack) FluidHelper.toRealFluidStack(fluidStack);
    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    public static ResourceLocation getStillTexture(com.lowdragmc.lowdraglib.side.fluid.FluidStack fluidStack) {
        return IClientFluidTypeExtensions.of(fluidStack.getFluid()).getStillTexture(getFluidStack(fluidStack));
    }
}
