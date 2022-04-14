package com.example.recipeforandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.se.omapi.Session;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.recipeforandroid.Network.NetworkCallback;
import com.example.recipeforandroid.Network.NetworkManager;
import com.example.recipeforandroid.Network.NetworkManager2;
import com.example.recipeforandroid.R;
import com.google.android.material.button.MaterialButton;

public class SignInActivity extends AppCompatActivity {

    private SharedPreferences mSp;
    private Context context;

    /**
     * Fall sem á að sjá um login og login samanburð til að skrá notanda inn
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportActionBar().hide();
        mSp = getSharedPreferences("login", MODE_PRIVATE);

        // Ef notandi er logged in er farið beint í RecipeList
        if(mSp.getBoolean("logged", false)){

            TextView username = (TextView) findViewById(R.id.username);
            goToRecipeList(username);
        }

        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);
        TextView register = (TextView) findViewById(R.id.register_hyperlink_text);

        MaterialButton login_button = (MaterialButton) findViewById(R.id.login_button);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NetworkManager2 netw = new NetworkManager2(getApplicationContext());
                netw.login(username.getText().toString(), password.getText().toString(), new NetworkCallback() {
                    @Override
                    public void onSuccess(Object result) {
                        mSp.edit().putBoolean("logged", true).apply();
                        mSp.edit().putString("user", username.getText().toString()).apply();
                        TextView username = (TextView) findViewById(R.id.username);
                        goToRecipeList(username);
                        // goToRecipeList();
                    }

                    @Override
                    public void onFailure(String errorString) {
                        System.out.println(errorString);
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

     public  void  goToRecipeList(TextView username) {
         Intent intent = new Intent(SignInActivity.this, RecipeListActivity.class);
         String user = username.getText().toString();
         intent.putExtra("userName", user);
         startActivity(intent);
     }
}