package com.epimorphismmc.monazite.integration.jade.provider;

import com.epimorphismmc.monazite.Monazite;
import com.epimorphismmc.monazite.config.MonaziteConfigHolder;
import com.epimorphismmc.monazite.utils.GTRecipeHelper;
import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.integration.jade.provider.CapabilityBlockProvider;
import com.lowdragmc.lowdraglib.side.fluid.FluidStack;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.fluid.JadeFluidObject;
import snownee.jade.api.ui.BoxStyle;
import snownee.jade.api.ui.IDisplayHelper;
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.api.ui.IProgressStyle;
import snownee.jade.util.FluidTextHelper;

import java.util.ArrayList;
import java.util.List;

public class RecipeFluidOutputInfoProvider extends CapabilityBlockProvider<RecipeLogic> {

    public static final RecipeFluidOutputInfoProvider INSTANCE = new RecipeFluidOutputInfoProvider();

    protected RecipeFluidOutputInfoProvider() {
        super(Monazite.id("recipe_info_fluid_output"));
    }

    @Nullable
    @Override
    protected RecipeLogic getCapability(Level level, BlockPos blockPos, @Nullable Direction direction) {
        return GTCapabilityHelper.getRecipeLogic(level, blockPos, direction);
    }

    @Override
    protected void write(CompoundTag compoundTag, RecipeLogic recipeLogic) {
        compoundTag.putBoolean("Working", recipeLogic.isWorking());
        var recipe = recipeLogic.getLastRecipe();
        if (recipe != null) {
            ListTag tags = new ListTag();
            for (var stack : GTRecipeHelper.getOutputFluid(recipe)) {
                if (stack != null && !stack.isEmpty()) {
                    var fluidTag = new CompoundTag();
                    stack.saveToTag(fluidTag);
                    tags.add(fluidTag);
                }
            }
            if (!tags.isEmpty()) {
                compoundTag.put("OutputFluids", tags);
            }
        }
    }

    @Override
    protected void addTooltip(CompoundTag compoundTag, ITooltip iTooltip, Player player, BlockAccessor blockAccessor, BlockEntity blockEntity, IPluginConfig iPluginConfig) {
        if (compoundTag.getBoolean("Working") && compoundTag.contains("OutputFluids", Tag.TAG_LIST)) {
            var tags = compoundTag.getList("OutputFluids", Tag.TAG_COMPOUND);
            if (!tags.isEmpty()) {
                List<FluidStack> fluidOutputs = new ArrayList<>();
                for (Tag tag : tags) {
                    if (tag instanceof CompoundTag tCompoundTag) {
                        var stack = FluidStack.loadFromTag(tCompoundTag);
                        if (!stack.isEmpty()) {
                            fluidOutputs.add(stack);
                        }
                    }
                }

                if (!fluidOutputs.isEmpty()) {
                    IElementHelper helper = IElementHelper.get();
                    iTooltip.add((Component.translatable("monazite.fluid.outputs").append(" ")));
                    for (FluidStack fluidOutput : fluidOutputs) {
                        if (fluidOutput != null && !fluidOutput.isEmpty()) {
                            if (MonaziteConfigHolder.INSTANCE.topInformation.conciseMode) {
                                iTooltip.add(getFluidName(fluidOutput).copy().append(" * " + ChatFormatting.AQUA + FluidTextHelper.getUnicodeMillibuckets(fluidOutput.getAmount(), true)));
                            } else {
                                Component text;
                                if (MonaziteConfigHolder.INSTANCE.topInformation.displayFluidName) {
                                    text = Component.translatable("jade.fluid", IDisplayHelper.get().stripColor(getFluidName(fluidOutput)), FluidTextHelper.getUnicodeMillibuckets(fluidOutput.getAmount(), true));
                                } else {
                                    text = Component.literal(FluidTextHelper.getUnicodeMillibuckets(fluidOutput.getAmount(), true));
                                }
                                IProgressStyle progressStyle = helper.progressStyle().overlay(IElementHelper.get().fluid(getFluid(fluidOutput)));
                                iTooltip.add(helper.progress(1, text, progressStyle, BoxStyle.DEFAULT, true));
                            }
                        }
                    }
                }
            }
        }
    }

    private Component getFluidName(FluidStack stack) {
        return ComponentUtils.wrapInSquareBrackets(stack.getDisplayName()).withStyle(ChatFormatting.WHITE);
    }

    private JadeFluidObject getFluid(FluidStack stack) {
        return JadeFluidObject.of(stack.getFluid(), stack.getAmount());
    }
}
