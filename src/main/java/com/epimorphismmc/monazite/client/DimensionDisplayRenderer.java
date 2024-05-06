package com.epimorphismmc.monazite.client;

import com.epimorphismmc.monazite.Monazite;
import com.gregtechceu.gtceu.client.model.SpriteOverrider;
import com.gregtechceu.gtceu.client.renderer.block.TextureOverrideRenderer;
import com.lowdragmc.lowdraglib.client.model.ModelFactory;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.BakedModelWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class DimensionDisplayRenderer extends TextureOverrideRenderer {

    public DimensionDisplayRenderer(String planetName) {
        super(new ResourceLocation("block/cube"), Map.of("up", Monazite.id("block/planet/%s/top".formatted(planetName)),
                "down", Monazite.id("block/planet/%s/bottom".formatted(planetName)),
                "north", Monazite.id("block/planet/%s/back".formatted(planetName)),
                "south", Monazite.id("block/planet/%s/front".formatted(planetName)),
                "east", Monazite.id("block/planet/%s/right".formatted(planetName)),
                "west", Monazite.id("block/planet/%s/left".formatted(planetName)),
                "particle", Monazite.id("block/planet/%s/front".formatted(planetName))));
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
