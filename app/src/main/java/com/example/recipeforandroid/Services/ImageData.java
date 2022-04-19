package com.example.recipeforandroid.Services;

public class ImageData {
    String id;
    String title;
    String url_viewer;
    String url;
    String display_url;
    String size;
    String time;
    String expiration;
    String delete_url;
    int is_360;

    UploadedImageInfo image;
    UploadedImageInfo thumb;
    UploadedImageInfo medium;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl_viewer() {
        return url_viewer;
    }

    public String getUrl() {
        return url;
    }

    public String getDisplay_url() {
        return display_url;
    }

    public String getSize() {
        return size;
    }

    public String getTime() {
        return time;
    }

    public String getExpiration() {
        return expiration;
    }

    public String getDelete_url() {
        return delete_url;
    }

    public int getIs_360() {
        return is_360;
    }

    public UploadedImageInfo getImage() {
        return image;
    }

    public UploadedImageInfo getThumb() {
        return thumb;
    }

    public UploadedImageInfo getMedium() {
        return medium;
    }
}
