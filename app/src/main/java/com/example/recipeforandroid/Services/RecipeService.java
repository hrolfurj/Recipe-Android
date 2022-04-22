package com.example.recipeforandroid.Services;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.recipeforandroid.Network.NetworkCallback;
import com.example.recipeforandroid.Network.NetworkManager;
import com.example.recipeforandroid.Persistence.Entities.Recipe;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Comparator;

public class RecipeService extends AppCompatActivity{

    public RecipeService() {
    }

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
            return Long.compare(r2.getID(), r1.getID());
        }
    };

    public static Comparator<Recipe> RecipeOldNewComparator = new Comparator<Recipe>() {
        @Override
        public int compare(Recipe r1, Recipe r2) {
            return Long.compare(r1.getID(), r2.getID());
        }
    };

    // Author: Kyle Clegg https://stackoverflow.com/questions/5776851/load-image-from-url
    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.NO_WRAP);
        return imageEncoded;
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

    public static void deleteRecipeFromList (Recipe recipe, Context context) {
        NetworkManager netw = new NetworkManager(context);
        netw.deleteRecipe(recipe, new NetworkCallback() {
            @Override
            public void onSuccess(Object result) {
            }

            @Override
            public void onFailure(String errorString) {
                System.out.println(errorString);
            }
        });
    }

    public void requestStoragePermission() {
        requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    public void requestCameraPermission() {
        requestPermissions(new String[] {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    public boolean checkStoragePermission(Context context) {
        boolean result = ContextCompat.checkSelfPermission(context,  Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
        return result;
    }

    public boolean checkCameraPermission(Context context) {
        boolean result1 = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED;
        boolean result2 = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
        return result1 && result2;
    }
}

