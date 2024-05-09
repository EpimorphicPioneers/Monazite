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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static com.epimorphismmc.monazite.common.data.MoBlocks.*;

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

    public GTOreVeinWidgetMixin() {
    }

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
        monazite$setupDimensionDisplay(ci);
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
        monazite$setupDimensionDisplay(ci);
    }

    @Unique
    private void monazite$setupDimensionDisplay(CallbackInfo ci) {
        if (MonaziteConfigHolder.INSTANCE.oreVeinDisplay.useNHDimensionDisplay && this.dimensionFilter != null) {
            int rowSlots = (width - 10) / 16 - 1;
            int interval = (width - 10 - 16 * rowSlots) / (rowSlots - 1);

            int count = 0;
            for (var dimension : dimensionFilter) {
                var location = dimension.location();
                var item = monazite$getDisplayItem(location);
                int row = Math.floorDiv(count, rowSlots);
                var transfer = new ItemStackTransfer();
                SlotWidget dimSlot = new SlotWidget(transfer, count,
                        5 + (16 + interval) * (count - row * rowSlots),
                        80 + 18 * row,
                        false, false).setIngredientIO(IngredientIO.BOTH);
                if (item != null) {
                    transfer.setStackInSlot(0, item);
                } else {
                    transfer.setStackInSlot(0, Blocks.GRASS.asItem().getDefaultInstance());
                    dimSlot.setHoverTooltips(location.toLanguageKey());
                }
                this.addWidget(dimSlot.setBackgroundTexture(IGuiTexture.EMPTY));
                count++;
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
