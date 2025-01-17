package com.epimorphismmc.monazite.integration.jade.provider;

import com.epimorphismmc.monazite.Monazite;
import com.gregtechceu.gtceu.api.blockentity.PipeBlockEntity;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import java.util.Locale;

public enum StainedColorProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getServerData().contains("StainedColor")) {
            int paintingColor = blockAccessor.getServerData().getInt("StainedColor");
            if (paintingColor != -1) {
                iTooltip.add(Component.translatable("monazite.stained", "#" + Integer.toHexString(paintingColor).toUpperCase(Locale.ROOT)).withStyle(Style.EMPTY.withColor(paintingColor)));
            }
        }
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        if (blockAccessor.getBlockEntity() instanceof IMachineBlockEntity blockEntity) {
            MetaMachine metaMachine = blockEntity.getMetaMachine();
            if (metaMachine != null) {
                int paintingColor = metaMachine.getPaintingColor();
                compoundTag.putInt("StainedColor", paintingColor);
            }
        } else if (blockAccessor.getBlockEntity() instanceof PipeBlockEntity<?, ?> pipe) {
            int paintingColor = pipe.getPaintingColor();
            compoundTag.putInt("StainedColor", paintingColor);
        }
    }

    @Override
    public ResourceLocation getUid() {
        return Monazite.id("stained_color");
    }
}
