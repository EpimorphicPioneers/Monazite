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
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

import static com.epimorphismmc.monazite.api.MonaziteAPI.*;
import static com.epimorphismmc.monazite.common.registry.MoRegistrate.*;

@SuppressWarnings("removal")
public class MoBlocks {

    private static final Pattern DIMENSION_PATTERN = Pattern.compile("(?<dimension>[^$]+)\\$(?<tier>[0-9]+)$");

    private static BlockEntry<Block> createDimensionDisplay(ResourceLocation dimension, int tier) {
        return MO_REGISTRATE.block("dimension_display.%s".formatted(dimension.toLanguageKey()),
                        p -> (Block) new RendererBlock(p, Platform.isClient() ? new DimensionDisplayRenderer(dimension, tier) : null))
                .addLayer(() -> RenderType::solid)
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
        dimensions.stream().filter(Objects::nonNull).forEach(dim -> {
            var matcher = DIMENSION_PATTERN.matcher(dim);
            if (matcher.matches()) {
                var dimension = matcher.group("dimension");
                var tier = Integer.parseInt(matcher.group("tier"));
                var loc = ResourceLocation.tryParse(dimension);
                if (loc != null) {
                    DIM_TIER.put(loc, tier);
                    ALL_DIM_DISPLAY_BLOCKS.put(loc, createDimensionDisplay(new ResourceLocation(dimension), tier));
                }
            }
        });
    }

}
