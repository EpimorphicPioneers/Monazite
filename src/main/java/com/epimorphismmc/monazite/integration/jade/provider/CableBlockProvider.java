package com.epimorphismmc.monazite.integration.jade.provider;

import com.epimorphismmc.monazite.Monazite;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class CableBlockProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {

    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {

    }

    @Override
    public ResourceLocation getUid() {
        return Monazite.id("cable_info");
    }
}
