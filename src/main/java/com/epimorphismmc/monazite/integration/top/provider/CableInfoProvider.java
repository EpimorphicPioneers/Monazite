package com.epimorphismmc.monazite.integration.top.provider;

import com.epimorphismmc.monazite.Monazite;
import com.epimorphismmc.monazite.utils.FormattingUtils;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.common.block.CableBlock;
import com.gregtechceu.gtceu.common.blockentity.CableBlockEntity;
import com.gregtechceu.gtceu.utils.GTUtil;
import mcjty.theoneprobe.api.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CableInfoProvider implements IProbeInfoProvider {
    @Override
    public ResourceLocation getID() {
        return Monazite.id("cable_info");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData iProbeHitData) {
        if (blockState.getBlock() instanceof CableBlock cableBlock) {
            CableBlockEntity cable = (CableBlockEntity) cableBlock.getPipeTile(level, iProbeHitData.getPos());
            if (cable != null) {
                long voltage = cable.getCurrentMaxVoltage();
                double amperage = cable.getAverageAmperage();
                IProbeInfo horizontalPane = iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
                horizontalPane.text(Component.translatable("monazite.cable.voltage"));
                if (voltage != 0) {
                    horizontalPane.text(GTValues.VNF[GTUtil.getTierByVoltage(voltage)]).text(" / ");
                }
                horizontalPane.text(GTValues.VNF[GTUtil.getTierByVoltage(cable.getMaxVoltage())]);

                horizontalPane = iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
                horizontalPane.text(Component.translatable("monazite.cable.amperage"));
                if (amperage != 0) {
                    horizontalPane.text(FormattingUtils.DECIMAL_FORMAT_1F.format(cable.getAverageAmperage()) + "A / ");
                }
                horizontalPane.text(FormattingUtils.DECIMAL_FORMAT_1F.format(cable.getMaxAmperage()) + "A");
            }
        }
    }
}
