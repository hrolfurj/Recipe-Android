package com.example.recipeforandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeListActivity extends AppCompatActivity {

    private static final String TAG = "Recipe Book";

    List<Recipes> recipeList = new ArrayList<Recipes>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        fillrecipeList();
        Log.d(TAG, "onCreate: " + recipeList.toString());

        Button addRecipeButton = (Button) findViewById(R.id.add_recipe_button);
        Button logoutButton = (Button) findViewById(R.id.Logout_button);
        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);

        TextView welcome = (TextView) findViewById(R.id.welcome_text);
        welcome.setText("Welcome " +sp.getString("user", "null") + "!");

        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeListActivity.this, NewRecipeActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recipe_list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RecycleViewAdapter(recipeList, RecipeListActivity.this);
        recyclerView.setAdapter(mAdapter);


        logoutButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeListActivity.this, LoginActivity.class);
                sp.edit().putBoolean("logged", false).apply();
                startActivity(intent);
            }
        });
    }

    private void fillrecipeList() {
        Recipes r0 = new Recipes("Pizza Mamaria", "Pizza", "Hveiti, vatn...", "");
        Recipes r1 = new Recipes("Carbonara", "Pasta", "Hveiti, vatn...", "");
        Recipes r2 = new Recipes("Chili Con Carne", "Hakk", "Hveiti, vatn...", "");
        Recipes r3 = new Recipes("Spaghetti & Meatballs", "Pasta", "Hveiti, vatn...", "");
        Recipes r4 = new Recipes("Plokkfiskur", "Fiskur", "Hveiti, vatn...", "");
        Recipes r5 = new Recipes("Lasagna", "Pasta", "Hveiti, vatn...", "");

        recipeList.addAll(Arrays.asList(new Recipes[] { r0, r1, r2, r3, r4 ,r5}));
    }
}