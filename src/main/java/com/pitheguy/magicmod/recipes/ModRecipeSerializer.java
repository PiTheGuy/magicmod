package com.pitheguy.magicmod.recipes;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class ModRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<MagicOrbRecipe>{

    @Override
    public MagicOrbRecipe read(ResourceLocation recipeId, JsonObject json) {
        ItemStack output = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "output"), true);
        Ingredient input1 = Ingredient.deserialize(JSONUtils.getJsonObject(json, "input1"));
        Ingredient input2 = Ingredient.deserialize(JSONUtils.getJsonObject(json, "input2"));
        Ingredient input3 = Ingredient.deserialize(JSONUtils.getJsonObject(json, "input3"));
        Ingredient input4 = Ingredient.deserialize(JSONUtils.getJsonObject(json, "input4"));
        Ingredient input5 = Ingredient.deserialize(JSONUtils.getJsonObject(json, "input5"));
        Ingredient input6 = Ingredient.deserialize(JSONUtils.getJsonObject(json, "input6"));
        Ingredient input7 = Ingredient.deserialize(JSONUtils.getJsonObject(json, "input7"));
        Ingredient input8 = Ingredient.deserialize(JSONUtils.getJsonObject(json, "input8"));
        Ingredient input9 = Ingredient.deserialize(JSONUtils.getJsonObject(json, "input9"));
        return new MagicOrbRecipe(recipeId,input1,input2,input3,input4,input5,input6,input7,input8,input9,output);
    }

    @Nullable
    @Override
    public MagicOrbRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
        ItemStack output = buffer.readItemStack();
        Ingredient input1 = Ingredient.read(buffer);
        Ingredient input2 = Ingredient.read(buffer);
        Ingredient input3 = Ingredient.read(buffer);
        Ingredient input4 = Ingredient.read(buffer);
        Ingredient input5 = Ingredient.read(buffer);
        Ingredient input6 = Ingredient.read(buffer);
        Ingredient input7 = Ingredient.read(buffer);
        Ingredient input8 = Ingredient.read(buffer);
        Ingredient input9 = Ingredient.read(buffer);
        return new MagicOrbRecipe(recipeId,input1,input2,input3,input4,input5,input6,input7,input8,input9,output);
    }

    @Override
    public void write(PacketBuffer buffer, MagicOrbRecipe recipe) {
        Ingredient input1 = recipe.getIngredients().get(0);
        Ingredient input2 = recipe.getIngredients().get(1);
        Ingredient input3 = recipe.getIngredients().get(2);
        Ingredient input4 = recipe.getIngredients().get(3);
        Ingredient input5 = recipe.getIngredients().get(4);
        Ingredient input6 = recipe.getIngredients().get(5);
        Ingredient input7 = recipe.getIngredients().get(6);
        Ingredient input8 = recipe.getIngredients().get(7);
        Ingredient input9 = recipe.getIngredients().get(8);
        buffer.writeItemStack(recipe.getRecipeOutput(), false);
    }
}
