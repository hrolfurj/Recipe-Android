package com.example.recipeforandroid.Persistence.Entities;



import java.util.List;

public class RecipeResponse {

    private List<Recipe2> recipes;

    public RecipeResponse(List<Recipe2> recipes) {
        this.recipes = recipes;
    }

    public List<Recipe2> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe2> recipes) {
        this.recipes = recipes;
    }
}
