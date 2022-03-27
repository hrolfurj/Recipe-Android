package com.example.recipeforandroid;


/**
 * Bráðabirgðar-entities fyrir RecycleView listann í RecipeListActivity.
 * TODO: Verður fjarlægt þegar gagnagrunnur verður tengdur og networkmaneger bætt við.
 */
public class Recipes {

    private int id;
    private String title;
    private String tag;
    private String description;
    private String upload_image;

    public Recipes(int id, String title, String tag, String description, String upload_image) {
        this.id =id;
        this.title = title;
        this.tag = tag;
        this.description = description;
        this.upload_image = upload_image;
    }


    @Override
    public String toString() {
        return "Recipes{" +
                "id=" + id +
                "title='" + title + '\'' +
                ", tag='" + tag + '\'' +
                ", description='" + description + '\'' +
                ", upload_image='" + upload_image + '\'' +
                '}';
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
