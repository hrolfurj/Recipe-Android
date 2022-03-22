package com.example.recipeforandroid;

public class Recipes {
    private String title;
    private String tag;
    private String description;
    private String upload_image;

    public Recipes(String title, String tag, String description, String upload_image) {
        this.title = title;
        this.tag = tag;
        this.description = description;
        this.upload_image = upload_image;
    }

    @Override
    public String toString() {
        return "Recipes{" +
                "title='" + title + '\'' +
                ", tag='" + tag + '\'' +
                ", description='" + description + '\'' +
                ", upload_image='" + upload_image + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpload_image() {
        return upload_image;
    }

    public void setUpload_image(String upload_image) {
        this.upload_image = upload_image;
    }
}
