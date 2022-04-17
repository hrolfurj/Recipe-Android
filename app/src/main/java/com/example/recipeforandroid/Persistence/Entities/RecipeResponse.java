package com.example.recipeforandroid.Persistence.Entities;



import java.util.List;

public class RecipeResponse {

    private List<Recipe> recipes;

    public RecipeResponse(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }
}
