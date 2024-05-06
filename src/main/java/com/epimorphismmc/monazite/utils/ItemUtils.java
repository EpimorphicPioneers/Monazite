package com.epimorphismmc.monazite.utils;

import com.epimorphismmc.monazite.Monazite;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ItemUtils {

    public static final Map<GTToolType, ItemStack> TOOL_CACHE = new HashMap<>();

    public static CompoundTag saveItemStack(ItemStack itemStack, CompoundTag compoundTag) {
        ResourceLocation resourceLocation = BuiltInRegistries.ITEM.getKey(itemStack.getItem());
        compoundTag.putString("id", resourceLocation.toString());
        compoundTag.putInt("Count", itemStack.getCount());
        if (itemStack.getTag() != null) {
            compoundTag.put("tag", itemStack.getTag().copy());
        }

        return compoundTag;
    }

    public static ItemStack loadItemStack(CompoundTag compoundTag) {
        try {
            Item item = BuiltInRegistries.ITEM.get(new ResourceLocation(compoundTag.getString("id")));
            int count = compoundTag.getInt("Count");
            ItemStack stack = new ItemStack(item, count);
            if (compoundTag.contains("tag", Tag.TAG_COMPOUND)) {
                stack.setTag(compoundTag.getCompound("tag"));
                if (stack.getTag() != null) {
                    stack.getItem().verifyTagAfterLoad(stack.getTag());
                }
            }

            if (stack.getItem().canBeDepleted()) {
                stack.setDamageValue(stack.getDamageValue());
            }
            return stack;
        } catch (RuntimeException var2) {
            Monazite.LOGGER.debug("Tried to load invalid item: {}", compoundTag, var2);
            return ItemStack.EMPTY;
        }
    }

    public static ItemStack getToolItem(GTToolType toolType) {
        return TOOL_CACHE.computeIfAbsent(toolType, type -> {
            if (type == GTToolType.SOFT_MALLET) {
                return GTItems.TOOL_ITEMS.get(GTMaterials.Rubber, type).asStack();
            }
            return GTItems.TOOL_ITEMS.get(GTMaterials.Aluminium, type).asStack();
        });
    }

}
