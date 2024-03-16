package com.epimorphismmc.monazite.integration.top.provider;

import com.epimorphismmc.monazite.Monazite;
import com.epimorphismmc.monazite.config.MonaziteConfigHolder;
import com.epimorphismmc.monazite.utils.GTRecipeHelper;
import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.integration.top.provider.CapabilityInfoProvider;
import mcjty.theoneprobe.api.*;
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

public class RecipeItemOutputInfoProvider extends CapabilityInfoProvider<RecipeLogic> {

    @Override
    public ResourceLocation getID() {
        return Monazite.id("recipe_info_item_output");
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
                List<ItemStack> itemOutputs = GTRecipeHelper.getOutputItem(recipe);
                if (!itemOutputs.isEmpty()) {
                    int drawnCount = 0;
                    horizontalPane.text(CompoundText.create().text(Component.translatable("monazite.item.outputs").append(" ")).style(TextStyleClass.INFO));
                    IProbeInfo pane = iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
                    for (ItemStack itemOutput : itemOutputs) {
                        if (itemOutput != null && !itemOutput.isEmpty()) {
                            if (MonaziteConfigHolder.INSTANCE.topInformation.conciseMode) {
                                pane.itemLabel(itemOutput).text(" * ").text(ChatFormatting.YELLOW + String.valueOf(itemOutput.getCount()));
                                pane = iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
                            } else {
                                if (MonaziteConfigHolder.INSTANCE.topInformation.displayItemName) {
                                    pane.item(itemOutput).itemLabel(itemOutput);
                                    pane = iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
                                } else {
                                    if (drawnCount >= MonaziteConfigHolder.INSTANCE.topInformation.itemsPerLine) {
                                        pane = iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
                                        pane.item(itemOutput);
                                        drawnCount = 0;
                                    } else {
                                        pane.item(itemOutput);
                                    }
                                }
                            }
                            drawnCount++;
                        }
                    }
                }
            }
        }
    }
}
