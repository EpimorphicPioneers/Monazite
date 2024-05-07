package com.epimorphismmc.monazite.integration.jade.provider;

import com.epimorphismmc.monazite.Monazite;
import com.epimorphismmc.monazite.api.jade.MoElementHelper;
import com.epimorphismmc.monazite.config.MonaziteConfigHolder;
import com.epimorphismmc.monazite.utils.RecipeUtils;
import com.epimorphismmc.monazite.utils.ItemUtils;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.fluid.JadeFluidObject;
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.util.FluidTextHelper;

import java.util.ArrayList;
import java.util.List;

public class RecipeOutputProvider extends CapabilityBlockProvider<RecipeLogic> {
    public static final RecipeOutputProvider INSTANCE = new RecipeOutputProvider();

    protected RecipeOutputProvider() {
        super(Monazite.id("recipe_output_info"));
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
    protected void write(CompoundTag compoundTag, RecipeLogic recipeLogic) {
        if (recipeLogic.isWorking()) {
            compoundTag.putBoolean("Working", recipeLogic.isWorking());
            var recipe = recipeLogic.getLastRecipe();
            if (recipe != null) {
                ListTag itemTags = new ListTag();
                for (var stack : RecipeUtils.getOutputItem(recipe)) {
                    if (stack != null && !stack.isEmpty()) {
                        var itemTag = new CompoundTag();
                        ItemUtils.saveItemStack(stack, itemTag);
                        itemTags.add(itemTag);
                    }
                }
                if (!itemTags.isEmpty()) {
                    compoundTag.put("OutputItems", itemTags);
                }
                ListTag fluidTags = new ListTag();
                for (var stack : RecipeUtils.getOutputFluid(recipe)) {
                    if (stack != null && !stack.isEmpty()) {
                        var fluidTag = new CompoundTag();
                        stack.saveToTag(fluidTag);
                        fluidTags.add(fluidTag);
                    }
                }
                if (!fluidTags.isEmpty()) {
                    compoundTag.put("OutputFluids", fluidTags);
                }
            }
        }
    }

    @Override
    protected void addTooltip(CompoundTag compoundTag, ITooltip iTooltip, Player player, BlockAccessor blockAccessor, BlockEntity blockEntity, IPluginConfig iPluginConfig) {
        if (compoundTag.getBoolean("Working")) {
            List<ItemStack> outputItems = new ArrayList<>();
            if (compoundTag.contains("OutputItems", Tag.TAG_LIST)) {
                ListTag itemTags = compoundTag.getList("OutputItems", Tag.TAG_COMPOUND);
                if (!itemTags.isEmpty()) {
                    for (Tag tag : itemTags) {
                        if (tag instanceof CompoundTag tCompoundTag) {
                            var stack = ItemUtils.loadItemStack(tCompoundTag);
                            if (!stack.isEmpty()) {
                                outputItems.add(stack);
                            }
                        }
                    }
                }
            }
            List<FluidStack> outputFluids = new ArrayList<>();
            if (compoundTag.contains("OutputFluids", Tag.TAG_LIST)) {
                ListTag fluidTags = compoundTag.getList("OutputFluids", Tag.TAG_COMPOUND);
                for (Tag tag : fluidTags) {
                    if (tag instanceof CompoundTag tCompoundTag) {
                        var stack = FluidStack.loadFromTag(tCompoundTag);
                        if (!stack.isEmpty()) {
                            outputFluids.add(stack);
                        }
                    }
                }
            }
            if (!outputItems.isEmpty() || !outputFluids.isEmpty()) {
                iTooltip.add(Component.translatable("monazite.recipe.output"));
            }
            addItemTooltips(iTooltip, outputItems);
            addFluidTooltips(iTooltip, outputFluids);
        }
    }

    private void addItemTooltips(ITooltip iTooltip, List<ItemStack> outputItems) {
        int drawnCount = 0;
        IElementHelper helper = iTooltip.getElementHelper();
        for (ItemStack itemOutput : outputItems) {
            if (itemOutput != null && !itemOutput.isEmpty()) {
                if (MonaziteConfigHolder.INSTANCE.topInformation.conciseMode) {
                    iTooltip.add(getItemName(itemOutput).copy().append(" * " + ChatFormatting.YELLOW + itemOutput.getCount()));
                } else {
                    int count = itemOutput.getCount();
                    itemOutput.setCount(1);
                    iTooltip.add(helper.smallItem(itemOutput));
                    Component text = Component.literal(" ")
                            .append(String.valueOf(count))
                            .append("× ")
                            .append(getItemName(itemOutput))
                            .withStyle(ChatFormatting.WHITE);
                    iTooltip.append(text);
//                    if (drawnCount >= MonaziteConfigHolder.INSTANCE.topInformation.itemsPerLine) {
//                        drawnCount = 0;
//                    }
//                    if (drawnCount == 0) {
//                        iTooltip.add(helper.item(itemOutput).translate(new Vec2(0, -5)));
//                    } else {
//                        iTooltip.append(helper.item(itemOutput).translate(new Vec2(0, -5)));
//                    }
                }
                drawnCount++;
            }
        }
    }

    private void addFluidTooltips(ITooltip iTooltip, List<FluidStack> outputFluids) {
        for (FluidStack fluidOutput : outputFluids) {
            if (fluidOutput != null && !fluidOutput.isEmpty()) {
                if (MonaziteConfigHolder.INSTANCE.topInformation.conciseMode) {
                    iTooltip.add(getFluidName(fluidOutput).copy()
                            .append(" * " + ChatFormatting.AQUA + FluidTextHelper.getUnicodeMillibuckets(fluidOutput.getAmount(), true)));
                } else {
                    iTooltip.add(MoElementHelper.smallFluid(getFluid(fluidOutput)));
                    Component text = Component.literal(" ")
                            .append(FluidTextHelper.getUnicodeMillibuckets(fluidOutput.getAmount(), true))
                            .append(" ")
                            .append(getFluidName(fluidOutput))
                            .withStyle(ChatFormatting.WHITE);
                    iTooltip.append(text);
                }
            }
        }
    }

    private Component getItemName(ItemStack stack) {
        return ComponentUtils.wrapInSquareBrackets(stack.getItem().getDescription()).withStyle(ChatFormatting.WHITE);
    }

    private Component getFluidName(FluidStack stack) {
        return ComponentUtils.wrapInSquareBrackets(stack.getDisplayName()).withStyle(ChatFormatting.WHITE);
    }

    private JadeFluidObject getFluid(FluidStack stack) {
        return JadeFluidObject.of(stack.getFluid(), stack.getAmount());
    }
}
