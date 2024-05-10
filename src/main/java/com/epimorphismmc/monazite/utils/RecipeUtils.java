package com.epimorphismmc.monazite.utils;

import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.lowdragmc.lowdraglib.side.fluid.FluidStack;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RecipeUtils {

    public static List<ItemStack> getOutputItem(GTRecipe recipe) {
        return new ArrayList<>(recipe.getOutputContents(ItemRecipeCapability.CAP).stream()
                .map(content -> ItemRecipeCapability.CAP.of(content.getContent()))
                .flatMap(ingredient -> Arrays.stream(ingredient.getItems()))
                .collect(Collectors.toMap(ItemStack::getItem, Function.identity(), (s1, s2) -> s1.copyWithCount(s1.getCount() + s2.getCount()))).values());
    }

    public static List<FluidStack> getOutputFluid(GTRecipe recipe) {
        return new ArrayList<>(recipe.getOutputContents(FluidRecipeCapability.CAP).stream()
                .map(content -> FluidRecipeCapability.CAP.of(content.getContent()))
                .flatMap(ingredient -> Arrays.stream(ingredient.getStacks()))
                .collect(Collectors.toMap(FluidStack::getFluid, Function.identity(), (s1, s2) -> s1.copy(s1.getAmount() + s2.getAmount()))).values());
    }

}
