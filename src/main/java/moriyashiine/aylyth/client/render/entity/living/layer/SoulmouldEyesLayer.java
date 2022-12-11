package moriyashiine.aylyth.client.render.entity.living.layer;

import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.mob.SoulmouldEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class SoulmouldEyesLayer extends GeoRenderLayer<SoulmouldEntity> {
    public SoulmouldEyesLayer(GeoRenderer<SoulmouldEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack poseStack, SoulmouldEntity animatable, BakedGeoModel bakedModel, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        super.render(poseStack, animatable, bakedModel, renderType, bufferSource, buffer, partialTick, packedLight, packedOverlay);
        Identifier location = new Identifier(Aylyth.MOD_ID, "textures/entity/living/mould/eyes.png");
        RenderLayer armor = RenderLayer.getEyes(location);
        getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, armor,
                bufferSource.getBuffer(armor), partialTick, packedLight, OverlayTexture.DEFAULT_UV,
                1, 1, 1, 1);
    }
}