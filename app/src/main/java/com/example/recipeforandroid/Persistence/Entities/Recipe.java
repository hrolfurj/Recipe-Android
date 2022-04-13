package com.example.recipeforandroid.Persistence.Entities;


import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

/**
 * Bráðabirgðar-entities fyrir RecycleView listann í RecipeListActivity.
 * TODO: Verður fjarlægt þegar gagnagrunnur verður tengdur og networkmaneger bætt við.
 */
public class Recipe {

    @SerializedName("ID")
    private int mId;
    @SerializedName("userID")
    private long mUserID;
    @SerializedName("recipeTitle")
    private String mTitle;
    @SerializedName("recipeTag")
    private String mTag;
    @SerializedName("recipeText")
    private String mDescription;
    @SerializedName("recipeImage")
    private String mUploadImage;


    public Recipe(int id, String title, String tag, String description, String upload_image) {
        this.mId =id;
        this.mTitle = title;
        this.mTag = tag;
        this.mDescription = description;
        this.mUploadImage = upload_image;
    }

    public static Comparator<Recipe> RecipeTitleAZComparator = new Comparator<Recipe>() {
        @Override
        public int compare(Recipe r1, Recipe r2) {
            return r1.getTitle().compareTo(r2.getTitle());
        }
    };

    public static Comparator<Recipe> RecipeTitleZAComparator = new Comparator<Recipe>() {
        @Override
        public int compare(Recipe r1, Recipe r2) {
            return r2.getTitle().compareTo(r1.getTitle());
        }
    };


    @Override
    public String toString() {
        return "Recipes{" +
                "id=" + mId +
                "title='" + mTitle + '\'' +
                ", tag='" + mTag + '\'' +
                ", description='" + mDescription + '\'' +
                ", upload_image='" + mUploadImage + '\'' +
                '}';
    }
    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getTag() {
        return mTag;
    }

    public void setTag(String tag) {
        this.mTag = tag;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getUploadImage() {
        return mUploadImage;
    }

    public void setUploadImage(String uploadImage) {
        this.mUploadImage = uploadImage;
    }
}
