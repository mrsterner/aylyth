package moriyashiine.aylyth.client.render.block;

import moriyashiine.aylyth.common.block.entity.SeepBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;

@Environment(EnvType.CLIENT)
public class SeepBlockEntityRenderer implements BlockEntityRenderer<SeepBlockEntity> { //just have different seep blocks duh, not dynamic rendering. use this to test portal rendering, then use block render event
	public SeepBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
	}
	
	@Override
	public void render(SeepBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		Matrix4f matrix = matrices.peek().getModel();
		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEndGateway()); //temporary
		this.renderSide(matrix, vertexConsumer, 0.0625F, 0.9375F, 0, 1, 0.9375F, 0.9375F, 0.9375F, 0.9375F);
		this.renderSide(matrix, vertexConsumer, 0.0625F, 0.9375F, 1, 0, 0.0625F, 0.0625F, 0.0625F, 0.0625F);
		this.renderSide(matrix, vertexConsumer, 0.9375F, 0.9375F, 1, 0, 0.0625F, 0.9375F, 0.9375F, 0.0625F);
		this.renderSide(matrix, vertexConsumer, 0.0625F, 0.0625F, 0, 1, 0.0625F, 0.9375F, 0.9375F, 0.0625F);
	}
	
	private void renderSide(Matrix4f model, VertexConsumer vertices, float x1, float x2, float y1, float y2, float z1, float z2, float z3, float z4) {
		vertices.vertex(model, x1, y1, z1).next();
		vertices.vertex(model, x2, y1, z2).next();
		vertices.vertex(model, x2, y2, z3).next();
		vertices.vertex(model, x1, y2, z4).next();
	}
}