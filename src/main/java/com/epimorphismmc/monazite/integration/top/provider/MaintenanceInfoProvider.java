package com.epimorphismmc.monazite.integration.top.provider;

import com.epimorphismmc.monazite.Monazite;
import com.epimorphismmc.monazite.config.MonaziteConfigHolder;
import com.epimorphismmc.monazite.utils.CapabilityUtils;
import com.epimorphismmc.monazite.utils.MoUtils;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMaintenanceMachine;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.gregtechceu.gtceu.integration.top.provider.CapabilityInfoProvider;
import mcjty.theoneprobe.api.CompoundText;
import mcjty.theoneprobe.api.ElementAlignment;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.apiimpl.styles.ItemStyle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public class MaintenanceInfoProvider extends CapabilityInfoProvider<IMaintenanceMachine> {
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
    protected void addProbeInfo(IMaintenanceMachine maintenanceMachine, IProbeInfo iProbeInfo, Player player, BlockEntity blockEntity, IProbeHitData iProbeHitData) {
        IProbeInfo verticalPane = iProbeInfo.vertical(iProbeInfo.defaultLayoutStyle().spacing(0));
        if (maintenanceMachine.hasMaintenanceProblems()) {
            if (player.isShiftKeyDown()) {
                int problems = maintenanceMachine.getMaintenanceProblems();
                for (byte i = 0; i < 6; i++) {
                    if (((problems >> i) & 1) == 0) {
                        IProbeInfo horizontalPane = verticalPane.horizontal(verticalPane.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
//                        ItemStack stack = ItemStack.EMPTY;
//                        String text = "";
//                        switch (i) {
//                            case 0: {
//                                stack = ItemUtils.getToolItem(GTToolType.WRENCH);
//                                text = "gtceu.multiblock.universal.problem.wrench";
//                                break;
//                            }
//                            case 1: {
//                                stack = ItemUtils.getToolItem(GTToolType.SCREWDRIVER);
//                                text = "gtceu.multiblock.universal.problem.screwdriver";
//                                break;
//                            }
//                            case 2: {
//                                stack = ItemUtils.getToolItem(GTToolType.SOFT_MALLET);
//                                text = "gtceu.multiblock.universal.problem.soft_mallet";
//                                break;
//                            }
//                            case 3: {
//                                stack = ItemUtils.getToolItem(GTToolType.HARD_HAMMER);
//                                text = "gtceu.multiblock.universal.problem.hard_hammer";
//                                break;
//                            }
//                            case 4: {
//                                stack = ItemUtils.getToolItem(GTToolType.WIRE_CUTTER);
//                                text = "gtceu.multiblock.universal.problem.wire_cutter";
//                                break;
//                            }
//                            case 5: {
//                                stack = ItemUtils.getToolItem(GTToolType.CROWBAR);
//                                text = "gtceu.multiblock.universal.problem.crowbar";
//                                break;
//                            }
//                        }
                        var tuple = MoUtils.getMaintenanceText(i);
                        horizontalPane.item(tuple.getA(), new ItemStyle().width(16).height(16)).text(Component.translatable(tuple.getB(), " "));
                    }
                }
            } else {
                verticalPane.text(CompoundText.create().error(Component.translatable("monazite.maintenance.broken")));
            }
        } else {
            verticalPane.text(CompoundText.create().ok(Component.translatable("monazite.maintenance.fixed")));
        }
    }

    @Override
    public ResourceLocation getID() {
        return Monazite.id("maintenance_info");
    }
}
