package com.epimorphismmc.monazite.api.top;

public interface IFluidStyle {
    IFluidStyle copy();

    IFluidStyle width(int var1);

    IFluidStyle height(int var1);

    default IFluidStyle bounds(int width, int height) {
        return this.width(width).height(height);
    }

    int getWidth();

    int getHeight();
}
