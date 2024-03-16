package com.epimorphismmc.monazite.integration.top.provider;

import com.epimorphismmc.monazite.Monazite;
import com.epimorphismmc.monazite.api.top.FluidStyle;
import com.epimorphismmc.monazite.config.MonaziteConfigHolder;
import com.epimorphismmc.monazite.integration.top.element.FluidStackElement;
import com.epimorphismmc.monazite.utils.GTRecipeHelper;
import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.integration.top.provider.CapabilityInfoProvider;
import com.lowdragmc.lowdraglib.gui.util.TextFormattingUtil;
import com.lowdragmc.lowdraglib.side.fluid.FluidStack;
import mcjty.theoneprobe.api.*;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RecipeFluidOutputInfoProvider extends CapabilityInfoProvider<RecipeLogic> {

    @Override
    public ResourceLocation getID() {
        return Monazite.id("recipe_info_fluid_output");
    }

    @Nullable
    @Override
    protected RecipeLogic getCapability(Level level, BlockPos blockPos, @Nullable Direction direction) {
        return GTCapabilityHelper.getRecipeLogic(level, blockPos, direction);
    }

    @Override
    protected void addProbeInfo(RecipeLogic recipeLogic, IProbeInfo iProbeInfo, Player player, BlockEntity blockEntity, IProbeHitData iProbeHitData) {
        if (recipeLogic.isWorking()) {
            var recipe = recipeLogic.getLastRecipe();
            if (recipe != null) {
                IProbeInfo horizontalPane = iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
                List<FluidStack> fluidOutputs = GTRecipeHelper.getOutputFluid(recipe);

                if (!fluidOutputs.isEmpty()) {
                    horizontalPane.text(CompoundText.create().text(Component.translatable("monazite.fluid.outputs").append(" ")).style(TextStyleClass.INFO));
                    for (FluidStack fluidOutput : fluidOutputs) {
                        if (fluidOutput != null && !fluidOutput.isEmpty()) {
                            if (MonaziteConfigHolder.INSTANCE.topInformation.conciseMode) {
                                IProbeInfo pane = iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
                                pane.text(CompoundText.create().text(fluidOutput.getDisplayName().copy().append(" * ").append(ChatFormatting.AQUA + TextFormattingUtil.formatLongToCompactStringBuckets(fluidOutput.getAmount(), 3) + "B")).style(TextStyleClass.INFO));
                            } else {
                                if (MonaziteConfigHolder.INSTANCE.topInformation.displayFluidName) {
                                    IProbeInfo pane = iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
                                    pane.element(new FluidStackElement(fluidOutput, new FluidStyle())).text(" ").text(fluidOutput.getDisplayName());
                                } else {
                                    horizontalPane.element(new FluidStackElement(fluidOutput, new FluidStyle()));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
