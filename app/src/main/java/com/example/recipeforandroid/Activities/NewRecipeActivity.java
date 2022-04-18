package com.example.recipeforandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.recipeforandroid.Network.NetworkCallback;
import com.example.recipeforandroid.Network.NetworkManager;
import com.example.recipeforandroid.Persistence.Entities.Recipe;
import com.example.recipeforandroid.R;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.InputStream;

public class NewRecipeActivity extends AppCompatActivity {
    private Button mSave_button;
    private Button mUpload_button;
    private ImageView mImage;
    private EditText mRecipeTitle;
    private EditText mRecipeText;
    private EditText mRecipeTag;
    private SharedPreferences mSp;

    /**
     * TODO: Ótengt við gagnagrunn, work in progress.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
        getSupportActionBar().hide();
        mSave_button = findViewById (R. id.save_recipe_button);
        mUpload_button = findViewById(R.id.upload_image_button);
        mImage = findViewById(R.id.new_recipe_image);
        mRecipeTitle = findViewById(R.id.input_Title);
        mRecipeText = findViewById(R.id.input_description);
        mRecipeTag = findViewById(R.id.input_Tag);
        boolean pick = true;

        mSp = getSharedPreferences("login", MODE_PRIVATE);

        /**
         * Sér um að opna myndavél eða gallerí eftir því hvað notandinn velur.
         */
        mUpload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pick == true){
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    } else PickImage();
                } else {
                    if (!checkStoragePermission()) {
                        requestStoragePermission();

                    } else PickImage();
                }
            }   
        });
        Intent fromSender = getIntent();
        String title = fromSender.getStringExtra("Title");
        String tag= fromSender.getStringExtra("Tag");
        String description = fromSender.getStringExtra("Description");
        Long recipeID = fromSender.getLongExtra("RecipeID", 0);
        System.out.println("RecipeID: " +recipeID);
        mRecipeText.setText(description);
        mRecipeTitle.setText(title);
        mRecipeTag.setText(tag);

        mSave_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recipe recipe = new Recipe();
                recipe.setUserID(mSp.getLong("userID", 0));
                recipe.setRecipeTitle(mRecipeTitle.getText().toString());
                recipe.setRecipeText(mRecipeText.getText().toString());
                recipe.setRecipeTag(mRecipeTag.getText().toString());
                recipe.setID(recipeID);
                System.out.println("RecipeID3: " +recipe.getID());

                NetworkManager netw = new NetworkManager(getApplicationContext());
                netw.saveRecipe(recipe, new NetworkCallback() {
                    @Override
                    public void onSuccess(Object result) {
                        Intent intent = new Intent(NewRecipeActivity.this, RecipeListActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(String errorString) {
                        System.out.println(errorString);
                    }
                });
            }
        });
    }

    private void PickImage() {
        CropImage.activity().start(this);
    }

    private void requestStoragePermission() {
        requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    private void requestCameraPermission() {
        requestPermissions(new String[] {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
        return result;
    }

    private boolean checkCameraPermission() {
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED;
        boolean result2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;
        return result1 && result2;
    }

    /**
     * Þetta fall sér um að ná í mynd sem notandinn tók með myndavél eða valdi úr gallerí, kropppar
     * myndina og birtir síðan.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try{
                    InputStream stream = getContentResolver().openInputStream(resultUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(stream);
                    mImage.setImageBitmap(bitmap);
                }catch (Exception e){
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}