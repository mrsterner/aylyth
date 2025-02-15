package moriyashiine.aylyth.client.render.entity.living;

import ladysnake.illuminations.client.particle.WispTrailParticleEffect;
import moriyashiine.aylyth.common.entity.mob.RippedSoulEntity;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;

public class RippedSoulEntityRenderer extends EntityRenderer<RippedSoulEntity> {
    public RippedSoulEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(RippedSoulEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        DefaultParticleType overhead = ParticleTypes.SOUL_FIRE_FLAME;
        if(FabricLoader.getInstance().isModLoaded("illuminations")) {
            entity.getWorld()
                    .addParticle(
                            new WispTrailParticleEffect(1.0F, 1.0F, 1.0F, -0.1F, -0.01F, 0.0F),
                            entity.getX() + entity.getRandom().nextGaussian() / 15.0,
                            entity.getY() + entity.getRandom().nextGaussian() / 15.0,
                            entity.getZ() + entity.getRandom().nextGaussian() / 15.0,
                            0.0,
                            0.0,
                            0.0
                    );
        } else {
            entity.getWorld().addParticle(overhead, entity.getX(), entity.getY(), entity.getZ(), 0, 0, 0);
        }
    }

    @Override
    public Identifier getTexture(RippedSoulEntity entity) {
        return null;
    }
}
