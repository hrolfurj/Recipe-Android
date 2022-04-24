package com.example.recipeforandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.example.recipeforandroid.Network.NetworkCallback;
import com.example.recipeforandroid.Network.NetworkManager;
import com.example.recipeforandroid.Persistence.Entities.Recipe;
import com.example.recipeforandroid.R;
import com.example.recipeforandroid.Helpers.ImageHost;
import com.example.recipeforandroid.Services.RecipeService;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;
import java.io.InputStream;

public class NewRecipeActivity extends AppCompatActivity {
    private static final String TAG = "NewRecipeActivity";
    private ImageView mImage;
    private EditText mRecipeTitle;
    private EditText mRecipeText;
    private EditText mRecipeTag;
    private SharedPreferences mSP;
    private String mBase64Image;
    private String mImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
        getSupportActionBar().hide();
        Button save_button = findViewById(R.id.save_recipe_button);
        Button upload_button = findViewById(R.id.upload_image_button);
        mImage = findViewById(R.id.new_recipe_image);
        mRecipeTitle = findViewById(R.id.input_Title);
        mRecipeText = findViewById(R.id.input_description);
        mRecipeTag = findViewById(R.id.input_Tag);
        boolean pick = true;

        mSP = getSharedPreferences("login", MODE_PRIVATE);

        /**
         * Sér um að opna myndavél eða gallerí eftir því hvað notandinn velur.
         */
        upload_button.setOnClickListener(view -> {
            RecipeService rs = new RecipeService();
            if (pick){
                if (!rs.checkCameraPermission(getApplicationContext())){
                    rs.requestCameraPermission();
                } else PickImage();
            } else {
                if (!rs.checkStoragePermission(getApplicationContext())) {
                    rs.requestStoragePermission();

                } else PickImage();
            }
        });
        Intent fromSender = getIntent();
        String title = fromSender.getStringExtra("Title");
        String tag= fromSender.getStringExtra("Tag");
        String description = fromSender.getStringExtra("Description");
        String image = fromSender.getStringExtra("Image");
        long recipeID = fromSender.getLongExtra("RecipeID", 0);
        mRecipeText.setText(description);
        mRecipeTitle.setText(title);
        mRecipeTag.setText(tag);

        new RecipeService.DownloadImageTask(mImage)
                .execute(image);

        save_button.setOnClickListener(view -> {
            Recipe recipe = new Recipe();
            recipe.setUserID(mSP.getLong("userID", 0));
            recipe.setRecipeTitle(mRecipeTitle.getText().toString());
            recipe.setRecipeText(mRecipeText.getText().toString());
            recipe.setRecipeTag(mRecipeTag.getText().toString());
            recipe.setID(recipeID);
            if (mImageUrl != null) {
                recipe.setRecipeImage(mImageUrl);
            }
            else recipe.setRecipeImage(image);

            NetworkManager netw = new NetworkManager(getApplicationContext());
            netw.saveRecipe(recipe, new NetworkCallback() {
                @Override
                public void onSuccess(Object result) {
                    Intent intent = new Intent(NewRecipeActivity.this, RecipeListActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(String errorString) {
                    Log.d(TAG, "errorString:" +errorString);
                }
            });
        });
    }

    private void PickImage() {
        CropImage.activity().start(this);
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
                    mBase64Image = RecipeService.encodeTobase64(bitmap);
                    mImage.setImageBitmap(bitmap);
                }catch (Exception e){
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

        NetworkManager netw = new NetworkManager(getApplicationContext());
        netw.uploadImage(mBase64Image, new NetworkCallback() {
            @Override
            public void onSuccess(Object result) {
                ImageHost imageHost = new Gson().fromJson(result.toString(), ImageHost.class);
                mImageUrl = imageHost.getData().getThumb().getUrl();
            }

            @Override
            public void onFailure(String errorString) {
                Log.d(TAG, "uploadImage:" +errorString);
            }
        });
    }
}