package com.pitheguy.magicmod.client.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.pitheguy.magicmod.entities.MagicFriend;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class MagicFriendModel<T extends MagicFriend> extends EntityModel<T> {
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
		textureWidth = 64;
		textureHeight = 64;

		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 11.0F, 9.0F);
		Body.setTextureOffset(0, 32).addBox(-5.0F, -8.0F, -20.0F, 10.0F, 10.0F, 22.0F, 0.0F, false);

		TailA = new ModelRenderer(this);
		TailA.setRotationPoint(0.0F, 4.0F, 11.0F);
		setRotationAngle(TailA, 0.5236F, 0.0F, 0.0F);
		TailA.setTextureOffset(42, 36).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 14.0F, 4.0F, 0.0F, false);

		Leg1A = new ModelRenderer(this);
		Leg1A.setRotationPoint(3.0F, 13.0F, 9.0F);
		Leg1A.setTextureOffset(48, 21).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.0F, true);

		Leg2A = new ModelRenderer(this);
		Leg2A.setRotationPoint(-3.0F, 13.0F, 9.0F);
		Leg2A.setTextureOffset(48, 21).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.0F, false);

		Leg3A = new ModelRenderer(this);
		Leg3A.setRotationPoint(3.0F, 13.0F, -9.0F);
		Leg3A.setTextureOffset(48, 21).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.0F, true);

		Leg4A = new ModelRenderer(this);
		Leg4A.setRotationPoint(-3.0F, 13.0F, -9.0F);
		Leg4A.setTextureOffset(48, 21).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.0F, false);

		Head = new ModelRenderer(this);
		Head.setRotationPoint(0.0F, -4.0F, -11.0F);
		setRotationAngle(Head, 0.5236F, 0.0F, 0.0F);
		Head.setTextureOffset(0, 13).addBox(-3.0F, -5.0F, -6.0F, 6.0F, 5.0F, 7.0F, 0.0F, false);
		Head.setTextureOffset(0, 25).addBox(-2.0F, -5.0F, -11.0F, 4.0F, 5.0F, 5.0F, 0.0F, false);

		Ear1 = new ModelRenderer(this);
		Ear1.setRotationPoint(0.0F, 7.0F, -8.0F);
		setRotationAngle(Ear1, 0.5236F, 0.0F, 0.0873F);
		Ear1.setTextureOffset(19, 16).addBox(-0.5F, -18.0F, 2.99F, 2.0F, 3.0F, 1.0F, 0.0F, true);

		Ear2 = new ModelRenderer(this);
		Ear2.setRotationPoint(0.0F, 7.0F, -8.0F);
		setRotationAngle(Ear2, 0.5236F, 0.0F, -0.0873F);
		Ear2.setTextureOffset(19, 16).addBox(-1.5F, -18.0F, 2.99F, 2.0F, 3.0F, 1.0F, 0.0F, false);

		MuleEarL = new ModelRenderer(this);
		MuleEarL.setRotationPoint(0.0F, 7.0F, -8.0F);
		setRotationAngle(MuleEarL, 0.5236F, 0.0F, 0.2618F);
		MuleEarL.setTextureOffset(0, 12).addBox(-3.0F, -22.0F, 2.99F, 2.0F, 7.0F, 1.0F, 0.0F, true);

		MuleEarR = new ModelRenderer(this);
		MuleEarR.setRotationPoint(0.0F, 7.0F, -8.0F);
		setRotationAngle(MuleEarR, 0.5236F, 0.0F, -0.2618F);
		MuleEarR.setTextureOffset(0, 12).addBox(1.0F, -22.0F, 2.99F, 2.0F, 7.0F, 1.0F, 0.0F, false);

		Neck = new ModelRenderer(this);
		Neck.setRotationPoint(0.0F, 7.0F, -8.0F);
		setRotationAngle(Neck, 0.5236F, 0.0F, 0.0F);
		Neck.setTextureOffset(0, 35).addBox(-2.0F, -11.0F, -3.0F, 4.0F, 12.0F, 7.0F, 0.0F, false);
		Neck.setTextureOffset(56, 36).addBox(-1.0F, -16.0F, 4.0F, 2.0F, 16.0F, 2.0F, 0.0F, false);

		Bag1 = new ModelRenderer(this);
		Bag1.setRotationPoint(-5.0F, 3.0F, 11.0F);
		setRotationAngle(Bag1, 0.0F, -1.5708F, 0.0F);
		Bag1.setTextureOffset(26, 21).addBox(-9.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, 0.0F, false);

		Bag2 = new ModelRenderer(this);
		Bag2.setRotationPoint(5.0F, 3.0F, 11.0F);
		setRotationAngle(Bag2, 0.0F, 1.5708F, 0.0F);
		Bag2.setTextureOffset(26, 21).addBox(1.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, 0.0F, true);

		Saddle = new ModelRenderer(this);
		Saddle.setRotationPoint(0.0F, 2.0F, 2.0F);
		Saddle.setTextureOffset(26, 0).addBox(-5.0F, 1.0F, -5.5F, 10.0F, 9.0F, 9.0F, 0.5F, false);

		SaddleMouthL = new ModelRenderer(this);
		SaddleMouthL.setRotationPoint(0.0F, 7.0F, -8.0F);
		setRotationAngle(SaddleMouthL, 0.5236F, 0.0F, 0.0F);
		SaddleMouthL.setTextureOffset(29, 5).addBox(2.0F, -14.0F, -6.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);

		SaddleMouthR = new ModelRenderer(this);
		SaddleMouthR.setRotationPoint(0.0F, 7.0F, -8.0F);
		setRotationAngle(SaddleMouthR, 0.5236F, 0.0F, 0.0F);
		SaddleMouthR.setTextureOffset(29, 5).addBox(-3.0F, -14.0F, -6.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);

		SaddleMouthLine = new ModelRenderer(this);
		SaddleMouthLine.setRotationPoint(0.0F, 7.0F, -8.0F);
		SaddleMouthLine.setTextureOffset(32, 2).addBox(3.1F, -10.0F, -11.5F, 0.0F, 3.0F, 16.0F, 0.0F, false);

		SaddleMouthLineR = new ModelRenderer(this);
		SaddleMouthLineR.setRotationPoint(0.0F, 7.0F, -8.0F);
		SaddleMouthLineR.setTextureOffset(32, 2).addBox(-3.1F, -10.0F, -11.5F, 0.0F, 3.0F, 16.0F, 0.0F, false);

		HeadSaddle = new ModelRenderer(this);
		HeadSaddle.setRotationPoint(0.0F, 7.0F, -8.0F);
		setRotationAngle(HeadSaddle, 0.5236F, 0.0F, 0.0F);
		HeadSaddle.setTextureOffset(19, 0).addBox(-2.0F, -16.0F, -5.0F, 4.0F, 5.0F, 2.0F, 0.25F, false);
		HeadSaddle.setTextureOffset(0, 0).addBox(-3.0F, -16.0F, -3.0F, 6.0F, 5.0F, 7.0F, 0.25F, false);
	}

	@Override
	public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		TailA.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		Leg1A.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		Leg2A.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		Leg3A.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		Leg4A.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		Head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		Ear1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		Ear2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		MuleEarL.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		MuleEarR.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		Neck.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		Bag1.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		Bag2.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		Saddle.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		SaddleMouthL.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		SaddleMouthR.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		SaddleMouthLine.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		SaddleMouthLineR.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		HeadSaddle.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
		super.setLivingAnimations(entityIn, limbSwing, limbSwingAmount, partialTick);
	}
}