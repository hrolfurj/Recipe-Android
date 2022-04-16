package com.example.recipeforandroid.Persistence.Entities;

import java.util.Comparator;

public class Recipe2 {

    private long userID;
    private String recipeTitle;
    private String recipeText;
    private String recipeTag;
    private String recipeImage;
    private long ID;
    private String recipeImagePath;

    public Recipe2() {

    }

    public Recipe2(long userID, String recipeTitle, String recipeText, String recipeTag, String recipeImage, long id, String recipeImagePath) {

        this.userID = userID;
        this.recipeTitle = recipeTitle;
        this.recipeText = recipeText;
        this.recipeTag = recipeTag;
        this.recipeImage = recipeImage;
        this.ID = ID;
        this.recipeImagePath = recipeImagePath;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public void setRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public String getRecipeText() {
        return recipeText;
    }

    public void setRecipeText(String recipeText) {
        this.recipeText = recipeText;
    }

    public String getRecipeTag() {
        return recipeTag;
    }

    public void setRecipeTag(String recipeTag) {
        this.recipeTag = recipeTag;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    public long getId() {
        return ID;
    }

    public void setId(long ID) {
        this.ID = ID;
    }

    public String getRecipeImagePath() {
        return recipeImagePath;
    }

    public void setRecipeImagePath(String recipeImagePath) {
        this.recipeImagePath = recipeImagePath;
    }


}
