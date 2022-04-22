package com.example.recipeforandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.recipeforandroid.R;
import com.example.recipeforandroid.Services.RecipeService;

public class ViewRecipeActivity extends AppCompatActivity {

    private Button mEdit_Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);
        getSupportActionBar().hide();

        String title = getIntent().getStringExtra("Title");
        String tag= getIntent().getStringExtra("Tag");
        String description = getIntent().getStringExtra("Description");
        Long recipeID = getIntent().getLongExtra("RecipeID", 0);
        String image = getIntent().getStringExtra("Image");
        TextView nameTextView1 = findViewById(R.id.input_title);
        TextView nameTextView3 = findViewById(R.id.input_description);
        nameTextView1.setText(title);
        nameTextView3.setText(description);
        nameTextView3.setMovementMethod(new ScrollingMovementMethod());
        new RecipeService.DownloadImageTask((ImageView) findViewById(R.id.view_image)).execute(image);

        mEdit_Button = findViewById(R.id.edit_button);
        mEdit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewRecipeActivity.this, NewRecipeActivity.class);
                intent.putExtra("Title", title);
                intent.putExtra("Tag", tag);
                intent.putExtra("Description", description);
                intent.putExtra("RecipeID", recipeID);
                intent.putExtra("Image", image);
                startActivity(intent);
            }
        });
    }
}