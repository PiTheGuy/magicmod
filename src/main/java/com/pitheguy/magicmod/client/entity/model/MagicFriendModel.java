// Made with Blockbench 4.2.2
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports
package com.pitheguy.magicmod.client.entity.model;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class MagicFriendModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("magicmod", "magic_friend"), "main");
	private final ModelPart Body;
	private final ModelPart TailA;
	private final ModelPart Leg1A;
	private final ModelPart Leg2A;
	private final ModelPart Leg3A;
	private final ModelPart Leg4A;
	private final ModelPart Head;
	private final ModelPart Ear1;
	private final ModelPart Ear2;
	private final ModelPart MuleEarL;
	private final ModelPart MuleEarR;
	private final ModelPart Neck;
	private final ModelPart Bag1;
	private final ModelPart Bag2;
	private final ModelPart Saddle;
	private final ModelPart SaddleMouthL;
	private final ModelPart SaddleMouthR;
	private final ModelPart SaddleMouthLine;
	private final ModelPart SaddleMouthLineR;
	private final ModelPart HeadSaddle;

	public MagicFriendModel(ModelPart root) {
		this.Body = root.getChild("Body");
		this.TailA = root.getChild("TailA");
		this.Leg1A = root.getChild("Leg1A");
		this.Leg2A = root.getChild("Leg2A");
		this.Leg3A = root.getChild("Leg3A");
		this.Leg4A = root.getChild("Leg4A");
		this.Head = root.getChild("Head");
		this.Ear1 = root.getChild("Ear1");
		this.Ear2 = root.getChild("Ear2");
		this.MuleEarL = root.getChild("MuleEarL");
		this.MuleEarR = root.getChild("MuleEarR");
		this.Neck = root.getChild("Neck");
		this.Bag1 = root.getChild("Bag1");
		this.Bag2 = root.getChild("Bag2");
		this.Saddle = root.getChild("Saddle");
		this.SaddleMouthL = root.getChild("SaddleMouthL");
		this.SaddleMouthR = root.getChild("SaddleMouthR");
		this.SaddleMouthLine = root.getChild("SaddleMouthLine");
		this.SaddleMouthLineR = root.getChild("SaddleMouthLineR");
		this.HeadSaddle = root.getChild("HeadSaddle");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 32).addBox(-5.0F, -8.0F, -20.0F, 10.0F, 10.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 11.0F, 9.0F));

		PartDefinition TailA = partdefinition.addOrReplaceChild("TailA", CubeListBuilder.create().texOffs(42, 36).addBox(-1.5F, 0.0F, -2.0F, 3.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 11.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition Leg1A = partdefinition.addOrReplaceChild("Leg1A", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 13.0F, 9.0F));

		PartDefinition Leg2A = partdefinition.addOrReplaceChild("Leg2A", CubeListBuilder.create().texOffs(48, 21).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 13.0F, 9.0F));

		PartDefinition Leg3A = partdefinition.addOrReplaceChild("Leg3A", CubeListBuilder.create().texOffs(48, 21).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 13.0F, -9.0F));

		PartDefinition Leg4A = partdefinition.addOrReplaceChild("Leg4A", CubeListBuilder.create().texOffs(48, 21).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 13.0F, -9.0F));

		PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -5.0F, -6.0F, 6.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(0, 25).addBox(-2.0F, -5.0F, -11.0F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, -11.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition Ear1 = partdefinition.addOrReplaceChild("Ear1", CubeListBuilder.create().texOffs(19, 16).mirror().addBox(-0.5F, -18.0F, 2.99F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 7.0F, -8.0F, 0.5236F, 0.0F, 0.0873F));

		PartDefinition Ear2 = partdefinition.addOrReplaceChild("Ear2", CubeListBuilder.create().texOffs(19, 16).addBox(-1.5F, -18.0F, 2.99F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, -8.0F, 0.5236F, 0.0F, -0.0873F));

		PartDefinition MuleEarL = partdefinition.addOrReplaceChild("MuleEarL", CubeListBuilder.create().texOffs(0, 12).mirror().addBox(-3.0F, -22.0F, 2.99F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 7.0F, -8.0F, 0.5236F, 0.0F, 0.2618F));

		PartDefinition MuleEarR = partdefinition.addOrReplaceChild("MuleEarR", CubeListBuilder.create().texOffs(0, 12).addBox(1.0F, -22.0F, 2.99F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, -8.0F, 0.5236F, 0.0F, -0.2618F));

		PartDefinition Neck = partdefinition.addOrReplaceChild("Neck", CubeListBuilder.create().texOffs(0, 35).addBox(-2.0F, -11.0F, -3.0F, 4.0F, 12.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(56, 36).addBox(-1.0F, -16.0F, 4.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, -8.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition Bag1 = partdefinition.addOrReplaceChild("Bag1", CubeListBuilder.create().texOffs(26, 21).addBox(-9.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 3.0F, 11.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition Bag2 = partdefinition.addOrReplaceChild("Bag2", CubeListBuilder.create().texOffs(26, 21).mirror().addBox(1.0F, 0.0F, 0.0F, 8.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, 3.0F, 11.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition Saddle = partdefinition.addOrReplaceChild("Saddle", CubeListBuilder.create().texOffs(26, 0).addBox(-5.0F, 1.0F, -5.5F, 10.0F, 9.0F, 9.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 2.0F, 2.0F));

		PartDefinition SaddleMouthL = partdefinition.addOrReplaceChild("SaddleMouthL", CubeListBuilder.create().texOffs(29, 5).addBox(2.0F, -14.0F, -6.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, -8.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition SaddleMouthR = partdefinition.addOrReplaceChild("SaddleMouthR", CubeListBuilder.create().texOffs(29, 5).addBox(-3.0F, -14.0F, -6.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, -8.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition SaddleMouthLine = partdefinition.addOrReplaceChild("SaddleMouthLine", CubeListBuilder.create().texOffs(32, 2).addBox(3.1F, -10.0F, -11.5F, 0.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, -8.0F));

		PartDefinition SaddleMouthLineR = partdefinition.addOrReplaceChild("SaddleMouthLineR", CubeListBuilder.create().texOffs(32, 2).addBox(-3.1F, -10.0F, -11.5F, 0.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, -8.0F));

		PartDefinition HeadSaddle = partdefinition.addOrReplaceChild("HeadSaddle", CubeListBuilder.create().texOffs(19, 0).addBox(-2.0F, -16.0F, -5.0F, 4.0F, 5.0F, 2.0F, new CubeDeformation(0.25F))
		.texOffs(0, 0).addBox(-3.0F, -16.0F, -3.0F, 6.0F, 5.0F, 7.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, 7.0F, -8.0F, 0.5236F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Body.render(poseStack, buffer, packedLight, packedOverlay);
		TailA.render(poseStack, buffer, packedLight, packedOverlay);
		Leg1A.render(poseStack, buffer, packedLight, packedOverlay);
		Leg2A.render(poseStack, buffer, packedLight, packedOverlay);
		Leg3A.render(poseStack, buffer, packedLight, packedOverlay);
		Leg4A.render(poseStack, buffer, packedLight, packedOverlay);
		Head.render(poseStack, buffer, packedLight, packedOverlay);
		Ear1.render(poseStack, buffer, packedLight, packedOverlay);
		Ear2.render(poseStack, buffer, packedLight, packedOverlay);
		MuleEarL.render(poseStack, buffer, packedLight, packedOverlay);
		MuleEarR.render(poseStack, buffer, packedLight, packedOverlay);
		Neck.render(poseStack, buffer, packedLight, packedOverlay);
		Bag1.render(poseStack, buffer, packedLight, packedOverlay);
		Bag2.render(poseStack, buffer, packedLight, packedOverlay);
		Saddle.render(poseStack, buffer, packedLight, packedOverlay);
		SaddleMouthL.render(poseStack, buffer, packedLight, packedOverlay);
		SaddleMouthR.render(poseStack, buffer, packedLight, packedOverlay);
		SaddleMouthLine.render(poseStack, buffer, packedLight, packedOverlay);
		SaddleMouthLineR.render(poseStack, buffer, packedLight, packedOverlay);
		HeadSaddle.render(poseStack, buffer, packedLight, packedOverlay);
	}
}