package com.example.recipeforandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ViewRecipeActivity extends AppCompatActivity {

    /**
     * TODO: PLACEHOLDER, work in progress.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);
        getSupportActionBar().hide();
    }
}