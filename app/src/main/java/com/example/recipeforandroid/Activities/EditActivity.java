package com.example.recipeforandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.recipeforandroid.R;

public class EditActivity extends AppCompatActivity {

    private Button mSave_button;

    /**
     * TODO: PLACEHOLDER, work in progress
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getSupportActionBar().hide();


        mSave_button = findViewById (R. id.save_button);
        mSave_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditActivity.this, RecipeListActivity.class);
                startActivity(intent);

            }
        });

    }
}