package com.example.recipeforandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RecipeListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button addRecipeButton = (Button) findViewById(R.id.add_recipe_button);
        Button logoutButton = (Button) findViewById(R.id.Logout_button);
        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);

        TextView welcome = (TextView) findViewById(R.id.welcome_text);
        welcome.setText("Welcome " +sp.getString("user", "null"));

        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeListActivity.this, NewRecipeActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeListActivity.this, LoginActivity.class);
                sp.edit().putBoolean("logged", false).apply();
                startActivity(intent);
            }
        });
    }
}