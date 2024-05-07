package com.epimorphismmc.monazite.integration.jade.provider;

import com.epimorphismmc.monazite.Monazite;
import com.epimorphismmc.monazite.config.MonaziteConfigHolder;
import com.epimorphismmc.monazite.utils.CapabilityUtils;
import com.epimorphismmc.monazite.utils.MoUtils;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMaintenanceMachine;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.gregtechceu.gtceu.integration.jade.provider.CapabilityBlockProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElementHelper;

public class MaintenanceBlockProvider extends CapabilityBlockProvider<IMaintenanceMachine> {

    public static final MaintenanceBlockProvider INSTANCE = new MaintenanceBlockProvider();

    protected MaintenanceBlockProvider() {
        super(Monazite.id("maintenance_info"));
    }

    @Nullable
    @Override
    protected IMaintenanceMachine getCapability(Level level, BlockPos blockPos, @Nullable Direction direction) {
        return CapabilityUtils.getMaintenanceMachine(level, blockPos, direction);
    }

    @Override
    protected boolean allowDisplaying(IMaintenanceMachine capability) {
        return ConfigHolder.INSTANCE.machines.enableMaintenance && MonaziteConfigHolder.INSTANCE.topInformation.displayMaintenanceInfo;
    }

    @Override
    protected void write(CompoundTag compoundTag, IMaintenanceMachine maintenanceMachine) {
        compoundTag.putBoolean("hasProblems", maintenanceMachine.hasMaintenanceProblems());
        if (maintenanceMachine.hasMaintenanceProblems()) {
            compoundTag.putInt("maintenanceProblems", maintenanceMachine.getMaintenanceProblems());
        }
    }

    @Override
    protected void addTooltip(CompoundTag compoundTag, ITooltip iTooltip, Player player, BlockAccessor blockAccessor, BlockEntity blockEntity, IPluginConfig iPluginConfig) {
        if (compoundTag.contains("hasProblems", Tag.TAG_BYTE)) {
            if (compoundTag.getBoolean("hasProblems")) {
                if (blockAccessor.showDetails()) {
                    int problems = compoundTag.getInt("maintenanceProblems");
                    for (byte i = 0; i < 6; i++) {
                        if (((problems >> i) & 1) == 0) {
                            var tuple = MoUtils.getMaintenanceText(i);
                            IElementHelper helper = iTooltip.getElementHelper();
                            iTooltip.add(helper.smallItem(tuple.getA()));
                            iTooltip.append(Component.translatable(tuple.getB(), ""));
                        }
                    }
                } else {
                    iTooltip.add(Component.translatable("monazite.maintenance.broken"));
                }
            } else {
                iTooltip.add(Component.translatable("monazite.maintenance.fixed"));
            }
        }
    }
}
