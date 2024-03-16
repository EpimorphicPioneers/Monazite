package com.epimorphismmc.monazite.utils;

import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.lowdragmc.lowdraglib.side.fluid.FluidStack;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GTRecipeHelper {

    public static List<ItemStack> getOutputItem(GTRecipe recipe) {
        List<ItemStack> stacks = new ArrayList<>();
        for (var content : recipe.getOutputContents(ItemRecipeCapability.CAP)) {
            stacks.addAll(List.of(ItemRecipeCapability.CAP.of(content.getContent()).getItems()));
        }
        return stacks;
    }

    public static List<FluidStack> getOutputFluid(GTRecipe recipe) {
        List<FluidStack> stacks = new ArrayList<>();
        for (var content : recipe.getOutputContents(FluidRecipeCapability.CAP)) {
            stacks.addAll(List.of(FluidRecipeCapability.CAP.of(content.getContent()).getStacks()));
        }
        return stacks;
    }

}
