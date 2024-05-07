package com.epimorphismmc.monazite.integration.jade.provider;

import com.epimorphismmc.monazite.Monazite;
import com.epimorphismmc.monazite.utils.CapabilityUtils;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.apache.commons.lang3.StringUtils;
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
            CompoundTag data = blockAccessor.getServerData().getCompound(getUid().toString());
            if (data.contains("autoOutputItem", Tag.TAG_COMPOUND)) {
                var tag = data.getCompound("autoOutputItem");
                addAutoOutputInfo(iTooltip, blockAccessor, tag, "monazite.auto_output.item");
            }

            if (data.contains("autoOutputFluid", Tag.TAG_COMPOUND)) {
                var tag = data.getCompound("autoOutputFluid");
                addAutoOutputInfo(iTooltip, blockAccessor, tag, "monazite.auto_output.fluid");
            }
        }
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        CompoundTag data = compoundTag.getCompound(getUid().toString());
        var level = blockAccessor.getLevel();
        var pos = blockAccessor.getPosition();
        var dir = blockAccessor.getSide();
        var autoOutputItem = CapabilityUtils.getAutoOutputItemMachine(level, pos, dir);
        var autoOutputFluid = CapabilityUtils.getAutoOutputFluidMachine(level, pos, dir);
        if (autoOutputItem != null) {
            var direction = autoOutputItem.getOutputFacingItems();
            if (direction != null) {
                data.put("autoOutputItem", writeData(new CompoundTag(), direction, blockAccessor, autoOutputItem.isAllowInputFromOutputSideItems(), autoOutputItem.isAutoOutputItems()));
            }
        }

        if (autoOutputFluid != null) {
            var direction = autoOutputFluid.getOutputFacingFluids();
            if (direction != null) {
                data.put("autoOutputFluid", writeData(new CompoundTag(), direction, blockAccessor, autoOutputFluid.isAllowInputFromOutputSideFluids(), autoOutputFluid.isAutoOutputFluids()));
            }
        }
        compoundTag.put(getUid().toString(), data);
    }

    @Override
    public ResourceLocation getUid() {
        return Monazite.id("auto_output_info");
    }

    private CompoundTag writeData(CompoundTag compoundTag, Direction direction, BlockAccessor blockAccessor, boolean allowInput, boolean auto) {
        compoundTag.putString("direction", direction.getName());
        var level = blockAccessor.getLevel();
        var pos = blockAccessor.getPosition().relative(direction);
        if (level != null) {
            var key = BuiltInRegistries.BLOCK.getKey(level.getBlockState(pos).getBlock());
            compoundTag.putString("block", key.toString());
        }
        compoundTag.putBoolean("allowInput", allowInput);
        compoundTag.putBoolean("auto", auto);
        return compoundTag;
    }

    private void addAutoOutputInfo(ITooltip iTooltip, BlockAccessor blockAccessor, CompoundTag compoundTag, String text) {
        var direction = Direction.byName(compoundTag.getString("direction"));
        boolean allowInput = compoundTag.getBoolean("allowInput");
        boolean auto = compoundTag.getBoolean("auto");
        if (direction != null) {
            iTooltip.add(Component.translatable(text, StringUtils.capitalize(direction.getName())));
            if (blockAccessor.showDetails()) {
                var block = BuiltInRegistries.BLOCK.get(new ResourceLocation(compoundTag.getString("block"))).asItem().getDefaultInstance();
                if (!block.isEmpty()) {
                    iTooltip.append(iTooltip.getElementHelper().smallItem(block));
                }
            }

            if (allowInput || auto) {
                var component = Component.literal(" (");
                if (auto) {
                    component.append(Component.translatable("monazite.auto_output.auto"));
                }

                if (allowInput && auto) {
                    component.append("/");
                }

                if (allowInput) {
                    component.append(Component.translatable("monazite.auto_output.allow_input"));
                }
                component.append(")");
                iTooltip.append(component);
            }
        }
    }
}
