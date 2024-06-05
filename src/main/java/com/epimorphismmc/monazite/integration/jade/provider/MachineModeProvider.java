package com.epimorphismmc.monazite.integration.jade.provider;

import com.epimorphismmc.monazite.Monazite;
import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum MachineModeProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getServerData().contains("RecipeType")) {
            ResourceLocation recipeType = new ResourceLocation(blockAccessor.getServerData().getString("RecipeType"));
            iTooltip.add(Component.translatable("monazite.machine_mode", Component.translatable("%s.%s".formatted(recipeType.getNamespace(), recipeType.getPath()))));
        }
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        if (blockAccessor.getBlockEntity() instanceof MetaMachineBlockEntity blockEntity) {
            @Nullable GTRecipeType[] recipeTypes = blockEntity.getMetaMachine().getDefinition().getRecipeTypes();
            if (recipeTypes.length > 1) {
                if (blockEntity.getMetaMachine() instanceof IRecipeLogicMachine recipeLogicMachine) {
                    compoundTag.putString("RecipeType", recipeLogicMachine.getRecipeType().registryName.toString());
                }
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return Monazite.id("machine_mode");
    }
}
