package com.epimorphismmc.monazite.integration.jade.provider;

import com.epimorphismmc.monazite.Monazite;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class AutoOutputBlockProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    public static final AutoOutputBlockProvider INSTANCE = new AutoOutputBlockProvider();

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        BlockEntity be = blockAccessor.getBlockEntity();
        if (be != null) {
            CompoundTag capData = blockAccessor.getServerData().getCompound(getUid().toString());

        }
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        CompoundTag capData = compoundTag.getCompound(getUid().toString());

        compoundTag.put(getUid().toString(), capData);
    }

    @Override
    public ResourceLocation getUid() {
        return Monazite.id("auto_output_info");
    }
}
