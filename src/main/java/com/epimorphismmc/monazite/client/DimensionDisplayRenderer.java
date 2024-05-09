package com.epimorphismmc.monazite.client;

import com.epimorphismmc.monazite.Monazite;
import com.epimorphismmc.monazite.config.MonaziteConfigHolder;
import com.gregtechceu.gtceu.client.model.SpriteOverrider;
import com.gregtechceu.gtceu.client.renderer.block.TextureOverrideRenderer;
import com.lowdragmc.lowdraglib.client.model.ModelFactory;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.BakedModelWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class DimensionDisplayRenderer extends TextureOverrideRenderer {

    private final int tier;

    public DimensionDisplayRenderer(ResourceLocation dimension, int tier) {
        super(new ResourceLocation("block/cube"), Map.of("up", Monazite.id("block/dim_display/%s/%s/top".formatted(dimension.getNamespace(), dimension.getPath())),
                "down", Monazite.id("block/dim_display/%s/%s/bottom".formatted(dimension.getNamespace(), dimension.getPath())),
                "north", Monazite.id("block/dim_display/%s/%s/back".formatted(dimension.getNamespace(), dimension.getPath())),
                "south", Monazite.id("block/dim_display/%s/%s/front".formatted(dimension.getNamespace(), dimension.getPath())),
                "east", Monazite.id("block/dim_display/%s/%s/right".formatted(dimension.getNamespace(), dimension.getPath())),
                "west", Monazite.id("block/dim_display/%s/%s/left".formatted(dimension.getNamespace(), dimension.getPath())),
                "particle", Monazite.id("block/dim_display/%s/%s/front".formatted(dimension.getNamespace(), dimension.getPath()))));
        this.tier = tier;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isGui3d() {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean useBlockLight(ItemStack stack) {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean useAO() {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderItem(ItemStack stack, ItemDisplayContext transformType, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay, BakedModel model) {
        super.renderItem(stack, transformType, leftHand, poseStack, buffer, combinedLight, combinedOverlay, model);
        if (MonaziteConfigHolder.INSTANCE.oreVeinDisplay.showDimensionTier && transformType == ItemDisplayContext.GUI) {
            Font fontRender = Minecraft.getInstance().font;
            float smallTextScale = 0.75F;
            String subscript = "T" + this.tier;

            poseStack.pushPose();
            poseStack.scale(0.0625F, -0.0625F, 0.0625F);
            poseStack.translate(-8.0F, -8.0F, 50.0F);
            poseStack.scale(smallTextScale, smallTextScale, 1.0F);

            fontRender.drawInBatch(subscript, 0.0F, (16.0F / smallTextScale) - 9.0F + 1.0F, 16777215, true, poseStack.last().pose(), buffer, Font.DisplayMode.NORMAL, 0, LightTexture.FULL_BRIGHT);
            poseStack.popPose();
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    protected @Nullable BakedModel getItemBakedModel() {
        if (this.itemModel == null) {
            UnbakedModel model = this.getModel();
            if (model instanceof BlockModel blockModel) {
                if (blockModel.getRootModel() == ModelBakery.GENERATION_MARKER) {
                    model = ModelFactory.ITEM_MODEL_GENERATOR.generateBlockModel(new SpriteOverrider(this.override), blockModel);
                }
            }

            BakedModel bakedModel = model.bake(ModelFactory.getModeBaker(),
                    new SpriteOverrider(this.override),
                    BlockModelRotation.X0_Y0,
                    this.modelLocation);
            if (bakedModel != null) {
                this.itemModel = new BakedModelWrapper<>(bakedModel) {
                    public boolean usesBlockLight() {
                        return false;
                    }

                    public boolean isGui3d() {
                        return false;
                    }
                };
            }
        }
        return this.itemModel;
    }
}
