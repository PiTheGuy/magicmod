package com.pitheguy.magicmod.client.entity.model;// Made with Blockbench 4.2.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.pitheguy.magicmod.entities.FluffyMagicianBare;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class FluffyMagicianBareModel<T extends Entity> extends EntityModel<T> {
	private final ModelRenderer Leg1;
	private final ModelRenderer Leg2;
	private final ModelRenderer Leg3;
	private final ModelRenderer Leg4;
	private final ModelRenderer Body;

	public FluffyMagicianBareModel() {
		texWidth = 64;
		texHeight = 64;

		Leg1 = new ModelRenderer(this);
		Leg1.setPos(0.0F, 24.0F, 0.0F);
		Leg1.texOffs(0, 0).addBox(-5.0F, -6.0F, 3.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);

		Leg2 = new ModelRenderer(this);
		Leg2.setPos(-2.0F, 24.0F, 0.0F);
		Leg2.texOffs(0, 0).addBox(5.0F, -6.0F, 3.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);

		Leg3 = new ModelRenderer(this);
		Leg3.setPos(0.0F, 24.0F, 0.0F);
		Leg3.texOffs(0, 0).addBox(-5.0F, -6.0F, -5.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);

		Leg4 = new ModelRenderer(this);
		Leg4.setPos(0.0F, 24.0F, 0.0F);
		Leg4.texOffs(0, 0).addBox(3.0F, -6.0F, -5.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);

		Body = new ModelRenderer(this);
		Body.setPos(0.0F, 24.0F, 0.0F);
		Body.texOffs(0, 0).addBox(-5.0F, -12.0F, -5.0F, 10.0F, 6.0F, 10.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Leg1.render(matrixStack, buffer, packedLight, packedOverlay);
		Leg2.render(matrixStack, buffer, packedLight, packedOverlay);
		Leg3.render(matrixStack, buffer, packedLight, packedOverlay);
		Leg4.render(matrixStack, buffer, packedLight, packedOverlay);
		Body.render(matrixStack, buffer, packedLight, packedOverlay);
	}
}