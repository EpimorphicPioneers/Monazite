package com.epimorphismmc.monazite.integration.jade;

import com.epimorphismmc.monazite.config.MonaziteConfigHolder;
import com.epimorphismmc.monazite.integration.jade.provider.RecipeFluidOutputInfoProvider;
import com.epimorphismmc.monazite.integration.jade.provider.RecipeItemOutputInfoProvider;
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
        if (MonaziteConfigHolder.INSTANCE.topInformation.displayItemOutputs) {
            registration.registerBlockDataProvider(RecipeItemOutputInfoProvider.INSTANCE, BlockEntity.class);
        }

        if (MonaziteConfigHolder.INSTANCE.topInformation.displayFluidOutputs) {
            registration.registerBlockDataProvider(RecipeFluidOutputInfoProvider.INSTANCE, BlockEntity.class);
        }
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        if (MonaziteConfigHolder.INSTANCE.topInformation.displayItemOutputs) {
            registration.registerBlockComponent(RecipeItemOutputInfoProvider.INSTANCE, Block.class);
        }

        if (MonaziteConfigHolder.INSTANCE.topInformation.displayFluidOutputs) {
            registration.registerBlockComponent(RecipeFluidOutputInfoProvider.INSTANCE, Block.class);
        }
    }


}
