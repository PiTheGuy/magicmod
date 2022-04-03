package com.pitheguy.magicmod.recipes;

import com.pitheguy.magicmod.init.RecipeSerializerInit;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class MagicOrbRecipe implements IModRecipe {
    private final ResourceLocation id;
    private Ingredient input1;
    private Ingredient input2;
    private Ingredient input3;
    private Ingredient input4;
    private Ingredient input5;
    private Ingredient input6;
    private Ingredient input7;
    private Ingredient input8;
    private Ingredient input9;
    private final ItemStack output;

    public MagicOrbRecipe(ResourceLocation id, Ingredient input1, Ingredient input2, Ingredient input3, Ingredient input4, Ingredient input5, Ingredient input6, Ingredient input7, Ingredient input8, Ingredient input9, ItemStack output) {
        this.id = id;
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
        this.input4 = input4;
        this.input5 = input5;
        this.input6 = input6;
        this.input7 = input7;
        this.input8 = input8;
        this.input9 = input9;
        this.output = output;
    }

    public boolean matches(RecipeWrapper inv, World worldIn) {
        return this.input1.test(inv.getStackInSlot(0)) && this.input2.test(inv.getStackInSlot(1)) && this.input3.test(inv.getStackInSlot(2)) && this.input4.test(inv.getStackInSlot(3)) && this.input5.test(inv.getStackInSlot(4)) && this.input6.test(inv.getStackInSlot(5)) && this.input7.test(inv.getStackInSlot(6)) && this.input8.test(inv.getStackInSlot(7)) && this.input9.test(inv.getStackInSlot(8));

    }

    @Override
    public ItemStack getCraftingResult(RecipeWrapper inv) {
        return this.output;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.output;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeSerializerInit.EXAMPLE_SERIALIZER.get();
    }

    @Override
    public Ingredient getInput1() {
        return input1;
    }

    @Override
    public Ingredient getInput2() {
        return input2;
    }

    @Override
    public Ingredient getInput3() {
        return input3;
    }

    @Override
    public Ingredient getInput4() {
        return input4;
    }

    @Override
    public Ingredient getInput5() {
        return input5;
    }

    @Override
    public Ingredient getInput6() {
        return input6;
    }

    @Override
    public Ingredient getInput7() {
        return input7;
    }

    @Override
    public Ingredient getInput8() {
        return input8;
    }

    @Override
    public Ingredient getInput9() {
        return input9;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.from(null, this.input1, this.input2, this.input3, this.input4, this.input5, this.input6, this.input7, this.input8, this.input9);
    }
}
