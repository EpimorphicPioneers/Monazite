package com.epimorphismmc.monazite.api;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class MonaziteAPI {
    public static final Object2ObjectOpenHashMap<ResourceLocation, Supplier<Block>> ALL_DIM_DISPLAY_BLOCKS = new Object2ObjectOpenHashMap<>();
    public static final Object2IntOpenHashMap<ResourceLocation> DIM_TIER = new Object2IntOpenHashMap<>();

    static {
        DIM_TIER.defaultReturnValue(-1);
    }
}
