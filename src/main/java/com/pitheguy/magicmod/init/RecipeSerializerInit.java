package com.pitheguy.magicmod.init;

import com.pitheguy.magicmod.recipes.IModRecipe;
import com.pitheguy.magicmod.recipes.MagicOrbRecipe;
import com.pitheguy.magicmod.recipes.ModRecipeSerializer;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeSerializerInit {
    public static final IRecipeSerializer<MagicOrbRecipe> EXAMPLE_RECIPE_SERIALIZER= new ModRecipeSerializer();
    public static final IRecipeType<IModRecipe> EXAMPLE_TYPE = registerType();

    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = new DeferredRegister<>(ForgeRegistries.RECIPE_SERIALIZERS, "magicmod");
    public static final RegistryObject<IRecipeSerializer<?>> EXAMPLE_SERIALIZER = RECIPE_SERIALIZERS.register("example", () -> EXAMPLE_RECIPE_SERIALIZER);


    private static <T extends IRecipeType<?>> T registerType() {
        return (T) Registry.register(Registry.RECIPE_TYPE, IModRecipe.RECIPE_TYPE_ID, new RecipeType<>());
    }

    private static class RecipeType<T extends IRecipe<?>> implements IRecipeType<T> {
        @Override
        public String toString() {
            return Registry.RECIPE_TYPE.getKey(this).toString();
        }
    }
}
