package com.example.recipeforandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class NewRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
        getSupportActionBar().hide();
    }
}