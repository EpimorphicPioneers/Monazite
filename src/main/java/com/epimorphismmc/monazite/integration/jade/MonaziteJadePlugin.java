package com.epimorphismmc.monazite.integration.jade;

import com.epimorphismmc.monazite.config.MonaziteConfigHolder;
import com.epimorphismmc.monazite.integration.jade.provider.MaintenanceInfoProvider;
import com.epimorphismmc.monazite.integration.jade.provider.RecipeOutputInfoProvider;
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
            registration.registerBlockDataProvider(RecipeOutputInfoProvider.INSTANCE, BlockEntity.class);
        }

        if (ConfigHolder.INSTANCE.machines.enableMaintenance && MonaziteConfigHolder.INSTANCE.topInformation.displayMaintenanceInfo) {
            registration.registerBlockDataProvider(MaintenanceInfoProvider.INSTANCE, BlockEntity.class);
        }
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        if (MonaziteConfigHolder.INSTANCE.topInformation.displayRecipeOutputs) {
            registration.registerBlockComponent(RecipeOutputInfoProvider.INSTANCE, Block.class);
        }

        if (ConfigHolder.INSTANCE.machines.enableMaintenance && MonaziteConfigHolder.INSTANCE.topInformation.displayMaintenanceInfo) {
            registration.registerBlockComponent(MaintenanceInfoProvider.INSTANCE, Block.class);
        }
    }

}
