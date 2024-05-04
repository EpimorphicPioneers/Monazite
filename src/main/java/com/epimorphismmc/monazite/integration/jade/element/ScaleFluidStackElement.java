package com.epimorphismmc.monazite.integration.jade.element;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import snownee.jade.api.fluid.JadeFluidObject;
import snownee.jade.impl.ui.FluidStackElement;

public class ScaleFluidStackElement extends FluidStackElement {
    private final float scale;

    public ScaleFluidStackElement(JadeFluidObject fluid, float scale) {
        super(fluid);
        this.scale = scale;
    }

    @Override
    public Vec2 getSize() {
        int size = Mth.floor(16.0f * scale);
        return new Vec2(size, size);
    }
}
