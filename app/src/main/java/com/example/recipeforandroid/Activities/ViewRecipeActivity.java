package com.example.recipeforandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recipeforandroid.R;

public class ViewRecipeActivity extends AppCompatActivity {

    private Button mEdit_Button;

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
        System.out.println("Title: " +title);
        String tag= getIntent().getStringExtra("Tag");
        String description = getIntent().getStringExtra("Description");
        Long recipeID = getIntent().getLongExtra("RecipeID", 0);
        /*String image = getIntent().getStringExtra("Image");*/

        TextView nameTextView1 = findViewById(R.id.input_title);
        TextView nameTextView2 = findViewById(R.id.input_tag);
        TextView nameTextView3 = findViewById(R.id.input_description);
        /*ImageView imageView = findViewById(R.id.view_image);*/

        nameTextView1.setText(title);
        nameTextView2.setText(tag);
        nameTextView3.setText(description);
        /*imageView.setImageResource(Integer.parseInt(image)); */

        mEdit_Button = findViewById(R.id.edit_button);
        mEdit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewRecipeActivity.this, NewRecipeActivity.class);
                intent.putExtra("Title", title);
                intent.putExtra("Tag", tag);
                intent.putExtra("Description", description);
                intent.putExtra("RecipeID", recipeID);
                startActivity(intent);

            }
        });
    }
}