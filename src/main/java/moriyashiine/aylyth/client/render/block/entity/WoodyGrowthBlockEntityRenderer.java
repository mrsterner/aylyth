package moriyashiine.aylyth.client.render.block.entity;

import moriyashiine.aylyth.client.RenderTypes;
import moriyashiine.aylyth.common.block.entity.WoodyGrowthCacheBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

public class WoodyGrowthBlockEntityRenderer implements BlockEntityRenderer<WoodyGrowthCacheBlockEntity> {

    public WoodyGrowthBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(WoodyGrowthCacheBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        Random rand = Random.create(42L);
        if (entity == null || !entity.hasWorld()) {
            return;
        }

        var state = entity.getCachedState();
        var consumer = VertexConsumers.union(vertexConsumers.getBuffer(RenderTypes.TINT), vertexConsumers.getBuffer(RenderLayer.getCutoutMipped()));
        var model = MinecraftClient.getInstance().getBlockRenderManager().getModel(state);
        var renderingSeed = state.getRenderingSeed(entity.getPos());
        rand.setSeed(renderingSeed);
        for (BakedQuad quad : model.getQuads(state, null, rand)) {
            consumer.quad(matrices.peek(), quad, 1.0f, 1.0f, 1.0f, light, overlay);
        }

        if (true || isPlayerWithinDistance(entity.getPos(), 24)) {
            var texture = getPlayerTexture(entity);
            if (texture != null) {
                matrices.push();
                matrices.translate(0.5, 2.25, 0.5);
                matrices.scale(0.35f, 0.35f, 0.35f);
                var gameTime = MinecraftClient.getInstance().world.getTime();
                matrices.translate(0, Math.sin(MathHelper.lerp(tickDelta, gameTime-1, gameTime) / 10D) / 10D, 0);
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(tickDelta, gameTime-1, gameTime)));
                matrices.translate(-0.5, -0.5, -0.5);
                var buffer = vertexConsumers.getBuffer(RenderTypes.ENTITY_NO_OUTLINE_DEPTH_FIX.apply(texture));
                renderBox(matrices, buffer, light, overlay);
                // TODO: Render second layer too
                matrices.pop();
            }
        }
    }

    @Nullable
    private Identifier getPlayerTexture(WoodyGrowthCacheBlockEntity entity) {
        var networkHandler = MinecraftClient.getInstance().getNetworkHandler();
        if (networkHandler != null) {
            var uuid = networkHandler.getPlayerListEntry(entity.getPlayerUuid());
            if (uuid != null) {
                return uuid.getSkinTexture();
            }
            return null;
        }
        return null;
    }

    private boolean isPlayerWithinDistance(BlockPos pos, double distance) {
        var playerDistSqr = MinecraftClient.getInstance().player.getPos().squaredDistanceTo(Vec3d.ofCenter(pos));
        return playerDistSqr <= distance * distance;
    }

    private void renderBox(MatrixStack matrices, VertexConsumer consumer, int light, int overlay) {
        var posMat = matrices.peek().getPositionMatrix();
        var norMat = matrices.peek().getNormalMatrix();

        //FRONT SIDE OF HEAD
        consumer.vertex(posMat, 1, 1, 0).color(255, 255, 255, 255).texture(0.125f, 0.125f).overlay(overlay).light(light).normal(norMat, 0, 0, -1).next();
        consumer.vertex(posMat, 1, 0, 0).color(255, 255, 255, 255).texture(0.125f, 0.25f).overlay(overlay).light(light).normal(norMat, 0, 0, -1).next();
        consumer.vertex(posMat, 0, 0, 0).color(255, 255, 255, 255).texture(0.25f, 0.25f).overlay(overlay).light(light).normal(norMat, 0, 0, -1).next();
        consumer.vertex(posMat, 0, 1, 0).color(255, 255, 255, 255).texture(0.25f, 0.125f).overlay(overlay).light(light).normal(norMat, 0, 0, -1).next();

        // RIGHT SIDE OF HEAD
        consumer.vertex(posMat, 0, 1, 0).color(255, 255, 255, 255).texture(0.25f, 0.125f).overlay(overlay).light(light).normal(norMat, 1, 0, 0).next();
        consumer.vertex(posMat, 0, 0, 0).color(255, 255, 255, 255).texture(0.25f, 0.25f).overlay(overlay).light(light).normal(norMat, 1, 0, 0).next();
        consumer.vertex(posMat, 0, 0, 1).color(255, 255, 255, 255).texture(0.375f, 0.25f).overlay(overlay).light(light).normal(norMat, 1, 0, 0).next();
        consumer.vertex(posMat, 0, 1, 1).color(255, 255, 255, 255).texture(0.375f, 0.125f).overlay(overlay).light(light).normal(norMat, 1, 0, 0).next();

        // BACK SIDE OF HEAD
        consumer.vertex(posMat, 0, 1, 1).color(255, 255, 255, 255).texture(0.375f, 0.125f).overlay(overlay).light(light).normal(norMat, 0, 0, -1).next();
        consumer.vertex(posMat, 0, 0, 1).color(255, 255, 255, 255).texture(0.375f, 0.25f).overlay(overlay).light(light).normal(norMat, 0, 0, -1).next();
        consumer.vertex(posMat, 1, 0, 1).color(255, 255, 255, 255).texture(0.5f, 0.25f).overlay(overlay).light(light).normal(norMat, 0, 0, -1).next();
        consumer.vertex(posMat, 1, 1, 1).color(255, 255, 255, 255).texture(0.5f, 0.125f).overlay(overlay).light(light).normal(norMat, 0, 0, -1).next();

        // LEFT SIDE OF HEAD
        consumer.vertex(posMat, 1, 1, 1).color(255, 255, 255, 255).texture(0, 0.125f).overlay(overlay).light(light).normal(norMat, -1, 0, 0).next();
        consumer.vertex(posMat, 1, 0, 1).color(255, 255, 255, 255).texture(0, 0.25f).overlay(overlay).light(light).normal(norMat, -1, 0, 0).next();
        consumer.vertex(posMat, 1, 0, 0).color(255, 255, 255, 255).texture(0.125f, 0.25f).overlay(overlay).light(light).normal(norMat, -1, 0, 0).next();
        consumer.vertex(posMat, 1, 1, 0).color(255, 255, 255, 255).texture(0.125f, 0.125f).overlay(overlay).light(light).normal(norMat, -1, 0, 0).next();

        // TOP SIDE OF HEAD
        consumer.vertex(posMat, 1, 1, 1).color(255, 255, 255, 255).texture(0.125f, 0).overlay(overlay).light(light).normal(norMat, 0, 1, 0).next();
        consumer.vertex(posMat, 1, 1, 0).color(255, 255, 255, 255).texture(0.125f, 0.125f).overlay(overlay).light(light).normal(norMat, 0, 1, 0).next();
        consumer.vertex(posMat, 0, 1, 0).color(255, 255, 255, 255).texture(0.25f, 0.125f).overlay(overlay).light(light).normal(norMat, 0, 1, 0).next();
        consumer.vertex(posMat, 0, 1, 1).color(255, 255, 255, 255).texture(0.25f, 0).overlay(overlay).light(light).normal(norMat, 0, 1, 0).next();

        // BOTTOM SIDE OF HEAD - NOTE: UV was rotated 180 due to weirdness
        consumer.vertex(posMat, 1, 0, 0).color(255, 255, 255, 255).texture(0.375f, 0.125f).overlay(overlay).light(light).normal(norMat, 0, -1, 0).next();
        consumer.vertex(posMat, 1, 0, 1).color(255, 255, 255, 255).texture(0.375f, 0).overlay(overlay).light(light).normal(norMat, 0, -1, 0).next();
        consumer.vertex(posMat, 0, 0, 1).color(255, 255, 255, 255).texture(0.25f, 0).overlay(overlay).light(light).normal(norMat, 0, -1, 0).next();
        consumer.vertex(posMat, 0, 0, 0).color(255, 255, 255, 255).texture(0.25f, 0.125f).overlay(overlay).light(light).normal(norMat, 0, -1, 0).next();
    }
}
