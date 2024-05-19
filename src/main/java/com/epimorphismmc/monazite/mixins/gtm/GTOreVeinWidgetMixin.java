package com.epimorphismmc.monazite.mixins.gtm;

import com.epimorphismmc.monazite.config.MonaziteConfigHolder;
import com.gregtechceu.gtceu.api.data.worldgen.GTOreDefinition;
import com.gregtechceu.gtceu.api.data.worldgen.bedrockfluid.BedrockFluidDefinition;
import com.gregtechceu.gtceu.integration.GTOreVeinWidget;
import com.lowdragmc.lowdraglib.gui.texture.IGuiTexture;
import com.lowdragmc.lowdraglib.gui.widget.SlotWidget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.jei.IngredientIO;
import com.lowdragmc.lowdraglib.misc.ItemStackTransfer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static com.epimorphismmc.monazite.api.MonaziteAPI.ALL_DIM_DISPLAY_BLOCKS;
import static com.epimorphismmc.monazite.api.MonaziteAPI.DIM_TIER;

@Mixin(value = GTOreVeinWidget.class, remap = false)
public abstract class GTOreVeinWidgetMixin extends WidgetGroup {

    @Unique
    private static final Map<ResourceLocation, ItemStack> DIM_MAP = new HashMap<>();

    @Shadow
    @Final
    public static int width;
    @Shadow
    @Final
    private Set<ResourceKey<Level>> dimensionFilter;

    public GTOreVeinWidgetMixin() {/**/}

    @Inject(
            method = {"setupText(Lcom/gregtechceu/gtceu/api/data/worldgen/GTOreDefinition;)V"},
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/gregtechceu/gtceu/integration/GTOreVeinWidget;addWidget(Lcom/lowdragmc/lowdraglib/gui/widget/Widget;)Lcom/lowdragmc/lowdraglib/gui/widget/WidgetGroup;",
                    ordinal = 5
            ),
            cancellable = true
    )
    private void setupText(GTOreDefinition oreDefinition, CallbackInfo ci) {
        monazite$setupDimensionDisplay(ci, 80);
    }

    @Inject(
            method = {"setupText(Lcom/gregtechceu/gtceu/api/data/worldgen/bedrockfluid/BedrockFluidDefinition;)V"},
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/gregtechceu/gtceu/integration/GTOreVeinWidget;addWidget(Lcom/lowdragmc/lowdraglib/gui/widget/Widget;)Lcom/lowdragmc/lowdraglib/gui/widget/WidgetGroup;",
                    ordinal = 3
            ),
            cancellable = true
    )
    private void setupText(BedrockFluidDefinition fluid, CallbackInfo ci) {
        monazite$setupDimensionDisplay(ci, 60);
    }

    @Unique
    private void monazite$setupDimensionDisplay(CallbackInfo ci, int yPosition) {
        if (MonaziteConfigHolder.INSTANCE.oreVeinDisplay.useNHDimensionDisplay && this.dimensionFilter != null) {
            int interval = 2;
            int rowSlots = (width - 10 + interval) / (16 + interval);

            var locations = dimensionFilter.stream()
                    .map(ResourceKey::location)
                    .sorted(Comparator.comparingInt(DIM_TIER::getInt))
                    .toArray(ResourceLocation[]::new);
            var transfer = new ItemStackTransfer(locations.length);
            for (int i = 0; i < locations.length; i++) {
                var location = locations[i];
                var item = monazite$getDisplayItem(location);
                int row = Math.floorDiv(i, rowSlots);
                SlotWidget dimSlot = new SlotWidget(transfer, i,
                        5 + (16 + interval) * (i - row * rowSlots),
                        yPosition + 18 * row,
                        false, false).setIngredientIO(IngredientIO.BOTH);
                if (item != null) {
                    transfer.setStackInSlot(i, item);
                } else {
                    transfer.setStackInSlot(i, Blocks.GRASS_BLOCK.asItem().getDefaultInstance());
                    dimSlot.setHoverTooltips(location.toLanguageKey());
                }
                this.addWidget(dimSlot.setBackgroundTexture(IGuiTexture.EMPTY));
            }
            ci.cancel();
        }
    }

    @Unique
    private ItemStack monazite$getDisplayItem(ResourceLocation dimension) {
        Supplier<Block> block = ALL_DIM_DISPLAY_BLOCKS.get(dimension);
        return block != null ? DIM_MAP.computeIfAbsent(dimension, d -> new ItemStack(block.get())) : null;
    }
}
