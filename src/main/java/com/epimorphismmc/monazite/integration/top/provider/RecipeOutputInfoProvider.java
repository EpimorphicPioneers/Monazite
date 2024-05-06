package com.epimorphismmc.monazite.integration.top.provider;

import com.epimorphismmc.monazite.Monazite;
import com.epimorphismmc.monazite.api.top.FluidStyle;
import com.epimorphismmc.monazite.config.MonaziteConfigHolder;
import com.epimorphismmc.monazite.integration.top.element.FluidStackElement;
import com.epimorphismmc.monazite.utils.RecipeUtils;
import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.integration.top.provider.CapabilityInfoProvider;
import com.lowdragmc.lowdraglib.gui.util.TextFormattingUtil;
import com.lowdragmc.lowdraglib.side.fluid.FluidStack;
import mcjty.theoneprobe.api.*;
import mcjty.theoneprobe.apiimpl.styles.ItemStyle;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RecipeOutputInfoProvider extends CapabilityInfoProvider<RecipeLogic> {

    @Override
    public ResourceLocation getID() {
        return Monazite.id("recipe_output_info");
    }

    @Nullable
    @Override
    protected RecipeLogic getCapability(Level level, BlockPos blockPos, @Nullable Direction direction) {
        return GTCapabilityHelper.getRecipeLogic(level, blockPos, direction);
    }

    @Override
    protected boolean allowDisplaying(RecipeLogic capability) {
        return MonaziteConfigHolder.INSTANCE.topInformation.displayRecipeOutputs;
    }

    @Override
    protected void addProbeInfo(RecipeLogic recipeLogic, IProbeInfo iProbeInfo, Player player, BlockEntity blockEntity, IProbeHitData iProbeHitData) {
        if (recipeLogic.isWorking()) {
            var recipe = recipeLogic.getLastRecipe();
            if (recipe != null) {
                IProbeInfo verticalPane = iProbeInfo.vertical(iProbeInfo.defaultLayoutStyle().spacing(0));
                verticalPane.text(CompoundText.create().info(Component.translatable("monazite.recipe.output").append(" ")));
                List<ItemStack> outputItems = RecipeUtils.getOutputItem(recipe);
                if (!outputItems.isEmpty()) {
                    addItemInfo(verticalPane, outputItems);
                }

                List<FluidStack> outputFluids = RecipeUtils.getOutputFluid(recipe);
                if (!outputFluids.isEmpty()) {
                    addFluidInfo(verticalPane, outputFluids);
                }
            }
        }
    }

    private void addItemInfo(IProbeInfo verticalPane, List<ItemStack> outputItems) {
        int drawnCount = 0;
        IProbeInfo horizontalPane;
        for (ItemStack itemOutput : outputItems) {
            if (itemOutput != null && !itemOutput.isEmpty()) {
                horizontalPane = verticalPane.horizontal(verticalPane.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
                if (MonaziteConfigHolder.INSTANCE.topInformation.conciseMode) {
                    horizontalPane.itemLabel(itemOutput).text(" * ").text(ChatFormatting.YELLOW + String.valueOf(itemOutput.getCount()));
                } else {
                    horizontalPane.item(itemOutput, new ItemStyle().width(16).height(16)).text(" ").itemLabel(itemOutput);
//                        if (drawnCount >= MonaziteConfigHolder.INSTANCE.topInformation.itemsPerLine) {
//                            pane = iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
//                            pane.item(itemOutput);
//                            drawnCount = 0;
//                        } else {
//                            pane.item(itemOutput);
//                        }
                }
            }
            drawnCount++;
        }
    }

    private void addFluidInfo(IProbeInfo verticalPane, List<FluidStack> outputFluids) {
        IProbeInfo horizontalPane;
        for (FluidStack fluidOutput : outputFluids) {
            if (fluidOutput != null && !fluidOutput.isEmpty()) {
                horizontalPane = verticalPane.horizontal(verticalPane.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
                if (MonaziteConfigHolder.INSTANCE.topInformation.conciseMode) {
                    horizontalPane.text(CompoundText.create().text(fluidOutput.getDisplayName().copy()
                            .append(" * ")
                            .append(ChatFormatting.AQUA + TextFormattingUtil.formatLongToCompactStringBuckets(fluidOutput.getAmount(), 3) + "B"))
                            .style(TextStyleClass.INFO));
                } else {
                    horizontalPane.element(new FluidStackElement(fluidOutput, new FluidStyle())).text(" ").text(fluidOutput.getDisplayName());
                }
            }
        }
    }

}
