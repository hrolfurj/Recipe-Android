package com.example.recipeforandroid.Services;

import com.example.recipeforandroid.Persistence.Entities.Recipe2;

import java.util.Comparator;

public class RecipeService {


    public static Comparator<Recipe2> RecipeTitleAZComparator = new Comparator<Recipe2>() {
        @Override
        public int compare(Recipe2 r1, Recipe2 r2) {
            return r1.getRecipeTitle().compareTo(r2.getRecipeTitle());
        }
    };

    public static Comparator<Recipe2> RecipeTitleZAComparator = new Comparator<Recipe2>() {
        @Override
        public int compare(Recipe2 r1, Recipe2 r2) {
            return r2.getRecipeText().compareTo(r1.getRecipeText());
        }
    };

}
