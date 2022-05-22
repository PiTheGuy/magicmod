package com.pitheguy.magicmod.client.entity.model;// Made with Blockbench 4.2.2
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class MagicFriendModel<T extends Entity> extends EntityModel<T> {
	private final ModelRenderer Body;
	private final ModelRenderer TailA;
	private final ModelRenderer Leg1A;
	private final ModelRenderer Leg2A;
	private final ModelRenderer Leg3A;
	private final ModelRenderer Leg4A;
	private final ModelRenderer Head;
	private final ModelRenderer Ear1;
	private final ModelRenderer Ear2;
	private final ModelRenderer MuleEarL;
	private final ModelRenderer MuleEarR;
	private final ModelRenderer Neck;
	private final ModelRenderer Bag1;
	private final ModelRenderer Bag2;
	private final ModelRenderer Saddle;
	private final ModelRenderer SaddleMouthL;
	private final ModelRenderer SaddleMouthR;
	private final ModelRenderer SaddleMouthLine;
	private final ModelRenderer SaddleMouthLineR;
	private final ModelRenderer HeadSaddle;

	public MagicFriendModel() {
		texWidth = 64;
		texHeight = 64;

		Body = new ModelRenderer(this);
		Body.setPos(0.0F, 11.0F, 9.0F);
		Body.texOffs(0, 32).addBox(-5.0F, -8.0F, -20.0F, 10.0F, 10.0F, 22.0F, 0.0F, false);

		TailA = new ModelRenderer(this);
		TailA.setPos(0.0F, 4.0F, 11.0F);
		setRotationAngle(TailA, 0.5236F, 0.0F, 0.0F);
		TailA.texOffs(42, 36).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 14.0F, 4.0F, 0.0F, false);

		Leg1A = new ModelRenderer(this);
		Leg1A.setPos(3.0F, 13.0F, 9.0F);
		Leg1A.texOffs(48, 21).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.0F, true);

		Leg2A = new ModelRenderer(this);
		Leg2A.setPos(-3.0F, 13.0F, 9.0F);
		Leg2A.texOffs(48, 21).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.0F, false);

		Leg3A = new ModelRenderer(this);
		Leg3A.setPos(3.0F, 13.0F, -9.0F);
		Leg3A.texOffs(48, 21).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.0F, true);

		Leg4A = new ModelRenderer(this);
		Leg4A.setPos(-3.0F, 13.0F, -9.0F);
		Leg4A.texOffs(48, 21).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.0F, false);

		Head = new ModelRenderer(this);
		Head.setPos(0.0F, -4.0F, -11.0F);
		setRotationAngle(Head, 0.5236F, 0.0F, 0.0F);
		Head.texOffs(0, 13).addBox(-3.0F, -5.0F, -6.0F, 6.0F, 5.0F, 7.0F, 0.0F, false);
		Head.texOffs(0, 25).addBox(-2.0F, -5.0F, -11.0F, 4.0F, 5.0F, 5.0F, 0.0F, false);

		Ear1 = new ModelRenderer(this);
		Ear1.setPos(0.0F, 7.0F, -8.0F);
		setRotationAngle(Ear1, 0.5236F, 0.0F, 0.0873F);
		Ear1.texOffs(19, 16).addBox(-0.5F, -18.0F, 2.99F, 2.0F, 3.0F, 1.0F, 0.0F, true);

		Ear2 = new ModelRenderer(this);
		Ear2.setPos(0.0F, 7.0F, -8.0F);
		setRotationAngle(Ear2, 0.5236F, 0.0F, -0.0873F);
		Ear2.texOffs(19, 16).addBox(-1.5F, -18.0F, 2.99F, 2.0F, 3.0F, 1.0F, 0.0F, false);

		MuleEarL = new ModelRenderer(this);
		MuleEarL.setPos(0.0F, 7.0F, -8.0F);
		setRotationAngle(MuleEarL, 0.5236F, 0.0F, 0.2618F);
		MuleEarL.texOffs(0, 12).addBox(-3.0F, -22.0F, 2.99F, 2.0F, 7.0F, 1.0F, 0.0F, true);

		MuleEarR = new ModelRenderer(this);
		MuleEarR.setPos(0.0F, 7.0F, -8.0F);
		setRotationAngle(MuleEarR, 0.5236F, 0.0F, -0.2618F);
		MuleEarR.texOffs(0, 12).addBox(1.0F, -22.0F, 2.99F, 2.0F, 7.0F, 1.0F, 0.0F, false);

		Neck = new ModelRenderer(this);
		Neck.setPos(0.0F, 7.0F, -8.0F);
		setRotationAngle(Neck, 0.5236F, 0.0F, 0.0F);
		Neck.texOffs(0, 35).addBox(-2.0F, -11.0F, -3.0F, 4.0F, 12.0F, 7.0F, 0.0F, false);
		Neck.texOffs(56, 36).addBox(-1.0F, -16.0F, 4.0F, 2.0F, 16.0F, 2.0F, 0.0F, false);

		Bag1 = new ModelRenderer(this);
		Bag1.setPos(-5.0F, 3.0F, 11.0F);
		setRotationAngle(Bag1, 0.0F, -1.5708F, 0.0F);
		Bag1.texOffs(26, 21).addBox(-9.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, 0.0F, false);

		Bag2 = new ModelRenderer(this);
		Bag2.setPos(5.0F, 3.0F, 11.0F);
		setRotationAngle(Bag2, 0.0F, 1.5708F, 0.0F);
		Bag2.texOffs(26, 21).addBox(1.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, 0.0F, true);

		Saddle = new ModelRenderer(this);
		Saddle.setPos(0.0F, 2.0F, 2.0F);
		Saddle.texOffs(26, 0).addBox(-5.0F, 1.0F, -5.5F, 10.0F, 9.0F, 9.0F, 0.5F, false);

		SaddleMouthL = new ModelRenderer(this);
		SaddleMouthL.setPos(0.0F, 7.0F, -8.0F);
		setRotationAngle(SaddleMouthL, 0.5236F, 0.0F, 0.0F);
		SaddleMouthL.texOffs(29, 5).addBox(2.0F, -14.0F, -6.0F, 1.0F, 2.0F, 2.0F, 0.0F, true);

		SaddleMouthR = new ModelRenderer(this);
		SaddleMouthR.setPos(0.0F, 7.0F, -8.0F);
		setRotationAngle(SaddleMouthR, 0.5236F, 0.0F, 0.0F);
		SaddleMouthR.texOffs(29, 5).addBox(-3.0F, -14.0F, -6.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);

		SaddleMouthLine = new ModelRenderer(this);
		SaddleMouthLine.setPos(0.0F, 7.0F, -8.0F);
		SaddleMouthLine.texOffs(32, 2).addBox(3.1F, -10.0F, -11.5F, 0.0F, 3.0F, 16.0F, 0.0F, false);

		SaddleMouthLineR = new ModelRenderer(this);
		SaddleMouthLineR.setPos(0.0F, 7.0F, -8.0F);
		SaddleMouthLineR.texOffs(32, 2).addBox(-3.1F, -10.0F, -11.5F, 0.0F, 3.0F, 16.0F, 0.0F, false);

		HeadSaddle = new ModelRenderer(this);
		HeadSaddle.setPos(0.0F, 7.0F, -8.0F);
		setRotationAngle(HeadSaddle, 0.5236F, 0.0F, 0.0F);
		HeadSaddle.texOffs(19, 0).addBox(-2.0F, -16.0F, -5.0F, 4.0F, 5.0F, 2.0F, 0.25F, false);
		HeadSaddle.texOffs(0, 0).addBox(-3.0F, -16.0F, -3.0F, 6.0F, 5.0F, 7.0F, 0.25F, false);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Body.render(matrixStack, buffer, packedLight, packedOverlay);
		TailA.render(matrixStack, buffer, packedLight, packedOverlay);
		Leg1A.render(matrixStack, buffer, packedLight, packedOverlay);
		Leg2A.render(matrixStack, buffer, packedLight, packedOverlay);
		Leg3A.render(matrixStack, buffer, packedLight, packedOverlay);
		Leg4A.render(matrixStack, buffer, packedLight, packedOverlay);
		Head.render(matrixStack, buffer, packedLight, packedOverlay);
		Ear1.render(matrixStack, buffer, packedLight, packedOverlay);
		Ear2.render(matrixStack, buffer, packedLight, packedOverlay);
		MuleEarL.render(matrixStack, buffer, packedLight, packedOverlay);
		MuleEarR.render(matrixStack, buffer, packedLight, packedOverlay);
		Neck.render(matrixStack, buffer, packedLight, packedOverlay);
		Bag1.render(matrixStack, buffer, packedLight, packedOverlay);
		Bag2.render(matrixStack, buffer, packedLight, packedOverlay);
		Saddle.render(matrixStack, buffer, packedLight, packedOverlay);
		SaddleMouthL.render(matrixStack, buffer, packedLight, packedOverlay);
		SaddleMouthR.render(matrixStack, buffer, packedLight, packedOverlay);
		SaddleMouthLine.render(matrixStack, buffer, packedLight, packedOverlay);
		SaddleMouthLineR.render(matrixStack, buffer, packedLight, packedOverlay);
		HeadSaddle.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}