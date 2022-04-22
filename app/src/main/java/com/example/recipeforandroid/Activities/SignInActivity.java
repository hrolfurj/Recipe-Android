package com.example.recipeforandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.recipeforandroid.Network.NetworkCallback;
import com.example.recipeforandroid.Network.NetworkManager;
import com.example.recipeforandroid.R;
import com.google.android.material.button.MaterialButton;
import org.json.JSONException;
import org.json.JSONObject;

public class SignInActivity extends AppCompatActivity {

    private SharedPreferences sp;

    /**
     * Fall sem á að sjá um login og login samanburð til að skrá notanda inn
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();
        sp = getSharedPreferences("login", MODE_PRIVATE);

        // Ef notandi er logged in er farið beint í RecipeList
        if(sp.getBoolean("logged", false)){
            goToRecipeList();
        }

        TextView username = findViewById(R.id.username);
        TextView password = findViewById(R.id.password);
        TextView register = findViewById(R.id.register_hyperlink_text);

        MaterialButton login_button = findViewById(R.id.login_button);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NetworkManager netw = new NetworkManager(getApplicationContext());
                netw.login(username.getText().toString(), password.getText().toString(), new NetworkCallback() {
                    @Override
                    public void onSuccess(Object result) {
                        sp.edit().putBoolean("logged", true).apply();
                        sp.edit().putString("user", username.getText().toString()).apply();

                        JSONObject temp = (JSONObject) result;
                        try {
                            Long idd = temp.getLong("id");
                            sp.edit().putLong("userID", idd).apply();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        goToRecipeList();
                    }

                    @Override
                    public void onFailure(String errorString) {
                        username.setText("");
                        password.setText("");
                        Toast.makeText(SignInActivity.this, R.string.login_failed_toast, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

     public  void  goToRecipeList() {
         Intent intent = new Intent(SignInActivity.this, RecipeListActivity.class);
         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
         startActivity(intent);
     }
}