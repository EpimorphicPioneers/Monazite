package com.epimorphismmc.monazite.common.data;

import com.epimorphismmc.monazite.api.MonaziteValues;
import com.epimorphismmc.monazite.client.DimensionDisplayRenderer;
import com.epimorphismmc.monazite.config.MonaziteConfigHolder;
import com.gregtechceu.gtceu.api.block.RendererBlock;
import com.gregtechceu.gtceu.api.item.RendererBlockItem;
import com.lowdragmc.lowdraglib.Platform;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;

import java.util.*;
import java.util.function.Supplier;

import static com.epimorphismmc.monazite.common.registry.MoRegistrate.MO_REGISTRATE;

@SuppressWarnings("removal")
public class MoBlocks {
    public static final Object2ObjectOpenHashMap<String, Supplier<Block>> ALL_DIM_DISPLAY_BLOCKS = new Object2ObjectOpenHashMap<>();

    private static BlockEntry<Block> createDimensionDisplay(String dimension) {
        return MO_REGISTRATE.block("dimension_display.%s".formatted(dimension),
                        p -> (Block) new RendererBlock(p, Platform.isClient() ? new DimensionDisplayRenderer(dimension) : null))
                .addLayer(() -> RenderType::cutoutMipped)
                .blockstate(NonNullBiConsumer.noop())
                .setData(ProviderType.LOOT, NonNullBiConsumer.noop())
                .setData(ProviderType.LANG, NonNullBiConsumer.noop())
                .item(RendererBlockItem::new)
                .model(NonNullBiConsumer.noop())
                .build()
                .register();
    }

    public static void init() {
        registerDimensionDisplay();
    }

    public static void registerDimensionDisplay() {
        Set<String> dimensions = new HashSet<>(List.of(MonaziteValues.DIM));
        dimensions.addAll(List.of(MonaziteConfigHolder.INSTANCE.oreVeinDisplay.dimensions));
        dimensions.stream().filter(Objects::nonNull).forEach(dim -> ALL_DIM_DISPLAY_BLOCKS.put(dim, createDimensionDisplay(dim)));
    }

}
