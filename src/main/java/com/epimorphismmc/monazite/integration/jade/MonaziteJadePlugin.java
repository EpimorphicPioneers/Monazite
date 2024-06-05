package com.epimorphismmc.monazite.integration.jade;

import com.epimorphismmc.monazite.config.MonaziteConfigHolder;
import com.epimorphismmc.monazite.integration.jade.provider.*;
import com.gregtechceu.gtceu.config.ConfigHolder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class MonaziteJadePlugin implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {
        if (MonaziteConfigHolder.INSTANCE.topInformation.displayRecipeOutputs) {
            registration.registerBlockDataProvider(RecipeOutputProvider.INSTANCE, BlockEntity.class);
        }

        if (MonaziteConfigHolder.INSTANCE.topInformation.displayMulitblockStructure) {
            registration.registerBlockDataProvider(MulitblockStructureProvider.INSTANCE, BlockEntity.class);
        }

        if (ConfigHolder.INSTANCE.machines.enableMaintenance && MonaziteConfigHolder.INSTANCE.topInformation.displayMaintenanceInfo) {
            registration.registerBlockDataProvider(MaintenanceBlockProvider.INSTANCE, BlockEntity.class);
        }

        if (MonaziteConfigHolder.INSTANCE.topInformation.displayExhaustVentInfo) {
            registration.registerBlockDataProvider(ExhaustVentBlockProvider.INSTANCE, BlockEntity.class);
        }

        if (MonaziteConfigHolder.INSTANCE.topInformation.displayAutoOutputInfo) {
            registration.registerBlockDataProvider(AutoOutputBlockProvider.INSTANCE, BlockEntity.class);
        }

        if (MonaziteConfigHolder.INSTANCE.topInformation.displayCableInfo) {
            registration.registerBlockDataProvider(CableBlockProvider.INSTANCE, BlockEntity.class);
        }

        if (MonaziteConfigHolder.INSTANCE.topInformation.displayMachineMode) {
            registration.registerBlockDataProvider(MachineModeProvider.INSTANCE, BlockEntity.class);
        }
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        if (MonaziteConfigHolder.INSTANCE.topInformation.displayRecipeOutputs) {
            registration.registerBlockComponent(RecipeOutputProvider.INSTANCE, Block.class);
        }

        if (MonaziteConfigHolder.INSTANCE.topInformation.displayMulitblockStructure) {
            registration.registerBlockComponent(MulitblockStructureProvider.INSTANCE, Block.class);
        }

        if (ConfigHolder.INSTANCE.machines.enableMaintenance && MonaziteConfigHolder.INSTANCE.topInformation.displayMaintenanceInfo) {
            registration.registerBlockComponent(MaintenanceBlockProvider.INSTANCE, Block.class);
        }

        if (MonaziteConfigHolder.INSTANCE.topInformation.displayExhaustVentInfo) {
            registration.registerBlockComponent(ExhaustVentBlockProvider.INSTANCE, Block.class);
        }

        if (MonaziteConfigHolder.INSTANCE.topInformation.displayAutoOutputInfo) {
            registration.registerBlockComponent(AutoOutputBlockProvider.INSTANCE, Block.class);
        }

        if (MonaziteConfigHolder.INSTANCE.topInformation.displayCableInfo) {
            registration.registerBlockComponent(CableBlockProvider.INSTANCE, Block.class);
        }

        if (MonaziteConfigHolder.INSTANCE.topInformation.displayMachineMode) {
            registration.registerBlockComponent(MachineModeProvider.INSTANCE, Block.class);
        }
    }

}
