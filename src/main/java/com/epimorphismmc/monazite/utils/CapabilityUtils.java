package com.epimorphismmc.monazite.utils;

import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.IAutoOutputFluid;
import com.gregtechceu.gtceu.api.machine.feature.IAutoOutputItem;
import com.gregtechceu.gtceu.api.machine.feature.IExhaustVentMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMaintenanceMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class CapabilityUtils {

    @Nullable
    public static IMaintenanceMachine getMaintenanceMachine(Level level, BlockPos pos, @Nullable Direction side) {
        var capability = GTCapabilityHelper.getMaintenanceMachine(level, pos, side);
        if (capability != null) return capability;
        if (MetaMachine.getMachine(level, pos) instanceof IMultiController controller) {
            for (var part : controller.getParts()) {
                if (part instanceof IMaintenanceMachine maintenanceMachine) {
                    return maintenanceMachine;
                }
            }
        }
        return null;
    }

    @Nullable
    public static IExhaustVentMachine getExhaustVentMachine(Level level, BlockPos pos, @Nullable Direction side) {
        if (MetaMachine.getMachine(level, pos) instanceof IExhaustVentMachine exhaustVentMachine) {
            return exhaustVentMachine;
        }
        return null;
    }

    @Nullable
    public static IAutoOutputItem getAutoOutputItemMachine(Level level, BlockPos pos, @Nullable Direction side) {
        if (MetaMachine.getMachine(level, pos) instanceof IAutoOutputItem autoOutputItem) {
            return autoOutputItem;
        }
        return null;
    }

    @Nullable
    public static IAutoOutputFluid getAutoOutputFluidMachine(Level level, BlockPos pos, @Nullable Direction side) {
        if (MetaMachine.getMachine(level, pos) instanceof IAutoOutputFluid autoOutputFluid) {
            return autoOutputFluid;
        }
        return null;
    }

}
