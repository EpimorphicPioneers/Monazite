package com.epimorphismmc.monazite.integration.jade.provider;

import com.epimorphismmc.monazite.Monazite;
import com.epimorphismmc.monazite.config.MonaziteConfigHolder;
import com.epimorphismmc.monazite.utils.GTRecipeHelper;
import com.epimorphismmc.monazite.utils.ItemUtil;
import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.integration.jade.provider.CapabilityBlockProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.Nullable;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElementHelper;

import java.util.ArrayList;
import java.util.List;

public class RecipeItemOutputInfoProvider extends CapabilityBlockProvider<RecipeLogic> {

    public static final RecipeItemOutputInfoProvider INSTANCE = new RecipeItemOutputInfoProvider();

    protected RecipeItemOutputInfoProvider() {
        super(Monazite.id("recipe_info_item_output"));
    }

    @Nullable
    @Override
    protected RecipeLogic getCapability(Level level, BlockPos blockPos, @Nullable Direction direction) {
        return GTCapabilityHelper.getRecipeLogic(level, blockPos, direction);
    }

    @Override
    protected void write(CompoundTag compoundTag, RecipeLogic recipeLogic) {
        compoundTag.putBoolean("Working", recipeLogic.isWorking());
        var recipe = recipeLogic.getLastRecipe();
        if (recipe != null) {
            ListTag tags = new ListTag();
            for (var stack : GTRecipeHelper.getOutputItem(recipe)) {
                if (stack != null && !stack.isEmpty()) {
                    var itemTag = new CompoundTag();
                    ItemUtil.saveItemStack(stack, itemTag);
                    tags.add(itemTag);
                }
            }
            if (!tags.isEmpty()) {
                compoundTag.put("OutputItems", tags);
            }
        }
    }

    @Override
    protected void addTooltip(CompoundTag compoundTag, ITooltip iTooltip, Player player, BlockAccessor blockAccessor, BlockEntity blockEntity, IPluginConfig iPluginConfig) {
        if (compoundTag.getBoolean("Working") && compoundTag.contains("OutputItems", Tag.TAG_LIST)) {
            var tags = compoundTag.getList("OutputItems", Tag.TAG_COMPOUND);
            if (!tags.isEmpty()) {
                List<ItemStack> outputItems = new ArrayList<>();
                for (Tag tag : tags) {
                    if (tag instanceof CompoundTag tCompoundTag) {
                        var stack = ItemUtil.loadItemStack(tCompoundTag);
                        if (!stack.isEmpty()) {
                            outputItems.add(stack);
                        }
                    }
                }

                if (!outputItems.isEmpty()) {
                    int drawnCount = 0;
                    IElementHelper helper = IElementHelper.get();
                    iTooltip.add((Component.translatable("monazite.item.outputs").append(" ")));
                    for (ItemStack itemOutput : outputItems) {
                        if (itemOutput != null && !itemOutput.isEmpty()) {
                            if (MonaziteConfigHolder.INSTANCE.topInformation.conciseMode) {
                                iTooltip.add(getItemName(itemOutput).copy().append(" * " + ChatFormatting.YELLOW + itemOutput.getCount()));
                            } else {
                                if (MonaziteConfigHolder.INSTANCE.topInformation.displayItemName) {
                                    int count = itemOutput.getCount();
                                    itemOutput.setCount(1);
                                    iTooltip.add(helper.smallItem(itemOutput));
                                    iTooltip.append(Component.literal(ChatFormatting.WHITE + String.valueOf(count) + "× "));
                                    iTooltip.append(getItemName(itemOutput));
                                } else {
                                    if (drawnCount >= MonaziteConfigHolder.INSTANCE.topInformation.itemsPerLine) {
                                        drawnCount = 0;
                                    }
                                    if (drawnCount == 0) {
                                        iTooltip.add(helper.item(itemOutput).translate(new Vec2(0, -5)));
                                    } else {
                                        iTooltip.append(helper.item(itemOutput).translate(new Vec2(0, -5)));
                                    }
                                }
                            }
                            drawnCount++;
                        }
                    }
                }
            }
        }
    }

    private Component getItemName(ItemStack stack) {
        return ComponentUtils.wrapInSquareBrackets(stack.getItem().getDescription()).withStyle(ChatFormatting.WHITE);
    }
}
