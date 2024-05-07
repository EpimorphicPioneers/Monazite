package com.epimorphismmc.monazite.integration.top.provider;

import com.epimorphismmc.monazite.Monazite;
import com.epimorphismmc.monazite.utils.CapabilityUtils;
import mcjty.theoneprobe.api.*;
import mcjty.theoneprobe.apiimpl.styles.ItemStyle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.StringUtils;

public class AutoOutputInfoProvider implements IProbeInfoProvider {

    @Override
    public ResourceLocation getID() {
        return Monazite.id("auto_output_info");
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player player, Level level, BlockState blockState, IProbeHitData iProbeHitData) {
        var pos = iProbeHitData.getPos();
        var dir = iProbeHitData.getSideHit();
        var autoOutputItem = CapabilityUtils.getAutoOutputItemMachine(level, pos, dir);
        var autoOutputFluid = CapabilityUtils.getAutoOutputFluidMachine(level, pos, dir);

        IProbeInfo verticalPane = iProbeInfo.vertical(iProbeInfo.defaultLayoutStyle().spacing(0));
        if (autoOutputItem != null) {
            var direction = autoOutputItem.getOutputFacingItems();
            addAutoOutputInfo(verticalPane, player, level, pos, direction, "monazite.auto_output.item", autoOutputItem.isAllowInputFromOutputSideItems(), autoOutputItem.isAutoOutputItems());
        }

        if (autoOutputFluid != null) {
            var direction = autoOutputFluid.getOutputFacingFluids();
            addAutoOutputInfo(verticalPane, player, level, pos, direction, "monazite.auto_output.fluid", autoOutputFluid.isAllowInputFromOutputSideFluids(), autoOutputFluid.isAutoOutputFluids());
        }
    }

    private void addAutoOutputInfo(IProbeInfo verticalPane, Player player, Level level, BlockPos blockPos, Direction direction, String text, boolean allowInput, boolean auto) {
        if (direction != null) {
            IProbeInfo horizontalPane = verticalPane.horizontal(verticalPane.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER));
            horizontalPane.text(CompoundText.create().info(Component.translatable(text, StringUtils.capitalize(direction.getName()) + " ")));
            if (player.isShiftKeyDown()) {
                if (level != null) {
                    var pos = blockPos.relative(direction);
                    var block = level.getBlockState(pos).getBlock().asItem().getDefaultInstance();
                    if (!block.isEmpty()) {
                        horizontalPane.item(block, new ItemStyle().width(16).height(16)).text(" ");
                    }
                }
            }

            if (allowInput || auto) {
                var compoundText = CompoundText.create().text("(");
                if (auto) {
                    compoundText.ok(Component.translatable("monazite.auto_output.auto"));
                }

                if (allowInput && auto) {
                    compoundText.style(TextStyleClass.INFO).text("/");
                }

                if (allowInput) {
                    compoundText.ok(Component.translatable("monazite.auto_output.allow_input"));
                }
                compoundText.style(TextStyleClass.INFO).text(")");
                horizontalPane.text(compoundText);
            }
        }
    }
}
