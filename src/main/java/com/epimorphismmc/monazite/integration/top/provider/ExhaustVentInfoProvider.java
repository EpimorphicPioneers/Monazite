package com.epimorphismmc.monazite.integration.top.provider;

import com.epimorphismmc.monazite.Monazite;
import com.epimorphismmc.monazite.utils.CapabilityUtils;
import com.gregtechceu.gtceu.api.machine.feature.IExhaustVentMachine;
import com.gregtechceu.gtceu.integration.top.provider.CapabilityInfoProvider;
import mcjty.theoneprobe.api.*;
import mcjty.theoneprobe.apiimpl.styles.ItemStyle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

public class ExhaustVentInfoProvider extends CapabilityInfoProvider<IExhaustVentMachine> {

    @Nullable
    @Override
    protected IExhaustVentMachine getCapability(Level level, BlockPos blockPos, @Nullable Direction direction) {
        return CapabilityUtils.getExhaustVentMachine(level, blockPos, direction);
    }

    @Override
    protected boolean allowDisplaying(IExhaustVentMachine capability) {
        return super.allowDisplaying(capability);
    }

    @Override
    protected void addProbeInfo(IExhaustVentMachine iExhaustVentMachine, IProbeInfo iProbeInfo, Player player, BlockEntity blockEntity, IProbeHitData iProbeHitData) {
        IProbeInfo horizontalPane = iProbeInfo.horizontal(iProbeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
        var direction = iExhaustVentMachine.getVentingDirection();
        horizontalPane.text(CompoundText.create().info(Component.translatable("monazite.exhaust_vent.direction", StringUtils.capitalize(direction.getName()) + " ")));
        if (!iExhaustVentMachine.isVentingBlocked()) return;

        if (player.isShiftKeyDown()) {
            var level = blockEntity.getLevel();
            var pos = blockEntity.getBlockPos().relative(direction);
            if (level != null) {
                var block = level.getBlockState(pos).getBlock().asItem().getDefaultInstance();
                horizontalPane.item(block, new ItemStyle().width(16).height(16)).text(" ");
            }
        }
        if (iExhaustVentMachine.isNeedsVenting()) {
            horizontalPane.text(CompoundText.create().text("(").error(Component.translatable("monazite.exhaust_vent.blocked")).style(TextStyleClass.INFO).text(")"));
        }
    }

    @Override
    public ResourceLocation getID() {
        return Monazite.id("exhaust_vent_info");
    }
}
