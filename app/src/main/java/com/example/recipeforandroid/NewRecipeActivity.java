package com.example.recipeforandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewRecipeActivity extends AppCompatActivity {
    Button save_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
        getSupportActionBar().hide();
        save_Button = findViewById (R. id.save_Button);

        save_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewRecipeActivity.this, RecipeListActivity.class);
                startActivity(intent);

            }
        });
    }
}