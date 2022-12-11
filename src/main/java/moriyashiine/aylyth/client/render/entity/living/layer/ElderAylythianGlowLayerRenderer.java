package moriyashiine.aylyth.client.render.entity.living.layer;

import moriyashiine.aylyth.client.RenderTypes;
import moriyashiine.aylyth.common.Aylyth;
import moriyashiine.aylyth.common.entity.mob.ElderAylythianEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

@Environment(EnvType.CLIENT)
public class ElderAylythianGlowLayerRenderer extends GeoRenderLayer<ElderAylythianEntity> {
	private static Identifier[] TEXTURE_LOCATIONS;
	
	public ElderAylythianGlowLayerRenderer(GeoRenderer<ElderAylythianEntity> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	public void render(MatrixStack poseStack, ElderAylythianEntity animatable, BakedGeoModel bakedModel, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
		if (TEXTURE_LOCATIONS == null) {
			TEXTURE_LOCATIONS = new Identifier[ElderAylythianEntity.VARIANTS];
			for (int i = 0; i < ElderAylythianEntity.VARIANTS; i++) {
				TEXTURE_LOCATIONS[i] = new Identifier(Aylyth.MOD_ID, "textures/entity/living/elder_aylythian/" + i + "_eyes.png");
			}
		}

		getRenderer().reRender(getDefaultBakedModel(animatable), poseStack, bufferSource, animatable, RenderTypes.getEntityTranslucent(TEXTURE_LOCATIONS[animatable.getDataTracker().get(ElderAylythianEntity.VARIANT)]),
				buffer, partialTick, 0xF000F0, OverlayTexture.DEFAULT_UV,
				1, 1, 1, 1);
	}
}
