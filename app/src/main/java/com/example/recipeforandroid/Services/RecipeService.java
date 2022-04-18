package com.example.recipeforandroid.Services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.example.recipeforandroid.Persistence.Entities.Recipe;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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
    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.NO_WRAP);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
