package com.epimorphismmc.monazite.utils;

import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;

public class MoUtils {

    public static Tuple<ItemStack, String> getMaintenanceText(byte flag) {
        return switch (flag) {
            case 0 -> new Tuple<>(ItemUtils.getToolItem(GTToolType.WRENCH), "gtceu.multiblock.universal.problem.wrench");
            case 1 -> new Tuple<>(ItemUtils.getToolItem(GTToolType.SCREWDRIVER), "gtceu.multiblock.universal.problem.screwdriver");
            case 2 -> new Tuple<>(ItemUtils.getToolItem(GTToolType.SOFT_MALLET), "gtceu.multiblock.universal.problem.soft_mallet");
            case 3 -> new Tuple<>(ItemUtils.getToolItem(GTToolType.HARD_HAMMER), "gtceu.multiblock.universal.problem.hard_hammer");
            case 4 -> new Tuple<>(ItemUtils.getToolItem(GTToolType.WIRE_CUTTER), "gtceu.multiblock.universal.problem.wire_cutter");
            default -> new Tuple<>(ItemUtils.getToolItem(GTToolType.CROWBAR), "gtceu.multiblock.universal.problem.crowbar");
        };
    }

}
