package com.example.recipeforandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recipeforandroid.R;

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

        String title = getIntent().getStringExtra("Title");
        String tag= getIntent().getStringExtra("Tag");
        String description = getIntent().getStringExtra("Description");
        /*String image = getIntent().getStringExtra("Image");*/

        TextView nameTextView1 = findViewById(R.id.input_title);
        TextView nameTextView2 = findViewById(R.id.input_tag);
        TextView nameTextView3 = findViewById(R.id.input_description);
        /*ImageView imageView = findViewById(R.id.view_image);*/

        nameTextView1.setText(title);
        nameTextView2.setText(tag);
        nameTextView3.setText(description);
        /*imageView.setImageResource(Integer.parseInt(image)); */
    }
}