package com.example.recipeforandroid.Services;

import com.example.recipeforandroid.Persistence.Entities.Recipe;

import java.util.Comparator;

public class RecipeService {


    public static Comparator<Recipe> RecipeTitleAZComparator = new Comparator<Recipe>() {
        @Override
        public int compare(Recipe r1, Recipe r2) {
            return r1.getRecipeTitle().compareTo(r2.getRecipeTitle());
        }
    };

    public static Comparator<Recipe> RecipeTitleZAComparator = new Comparator<Recipe>() {
        @Override
        public int compare(Recipe r1, Recipe r2) {
            return r2.getRecipeText().compareTo(r1.getRecipeText());
        }
    };

    public static Comparator<Recipe> RecipeNewOldComparator = new Comparator<Recipe>() {
        @Override
        public int compare(Recipe r1, Recipe r2) {
            return Long.compare(r1.getID(), r2.getID());
        }
    };

    public static Comparator<Recipe> RecipeOldNewComparator = new Comparator<Recipe>() {
        @Override
        public int compare(Recipe r1, Recipe r2) {
            //return r2.getRecipeTitle().c
            return Long.compare(r2.getID(), r1.getID());
        }
    };

}
