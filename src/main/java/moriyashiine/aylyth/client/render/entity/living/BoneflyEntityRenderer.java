package moriyashiine.aylyth.client.render.entity.living;


import moriyashiine.aylyth.client.model.entity.BoneflyEntityModel;
import moriyashiine.aylyth.common.entity.mob.BoneflyEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BoneflyEntityRenderer extends GeoEntityRenderer<BoneflyEntity> {
    public BoneflyEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BoneflyEntityModel());
    }

    @Override
    public RenderLayer getRenderType(BoneflyEntity animatable, Identifier texture, @Nullable VertexConsumerProvider bufferSource, float partialTick) {
        return RenderLayer.getEntityTranslucent(texture, true);
    }
}