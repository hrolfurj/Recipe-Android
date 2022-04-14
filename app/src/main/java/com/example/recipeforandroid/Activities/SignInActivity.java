package com.example.recipeforandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
     * Eins og er, bara harðkóðað fyrir "username - admin" og "password - admin".
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
            goToRecipeList();
        }

        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);
        TextView register = (TextView) findViewById(R.id.register_hyperlink_text);

        MaterialButton login_button = (MaterialButton) findViewById(R.id.login_button);


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "https://www.google.com";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                System.out.println("onResponse");
                                System.out.println(response.substring(0,500));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("That didn't work!");
                    }
                });

// Add the request to the RequestQueue.
                queue.add(stringRequest);*/
                NetworkManager2 netw = new NetworkManager2(getApplicationContext());
                netw.login(username.getText().toString(), password.getText().toString(), new NetworkCallback() {
                    @Override
                    public void onSuccess(Object result) {
                        mSp.edit().putBoolean("logged", true).apply();
                        mSp.edit().putString("user", username.getText().toString()).apply();
                        goToRecipeList();
                    }

                    @Override
                    public void onFailure(String errorString) {
                        System.out.println(errorString);
                        Toast.makeText(SignInActivity.this, R.string.login_failed_toast, Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
                /**if(loggedIn) {
                //if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                    //correct
                    mSp.edit().putBoolean("logged", true).apply();
                    mSp.edit().putString("user", username.getText().toString()).apply();
                    goToRecipeList();
                } else
                    //incorrect
                    System.out.println("incorrect username/passw");
                    Toast.makeText(SignInActivity.this, R.string.login_failed_toast, Toast.LENGTH_SHORT).show();
            }
        });*/

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
         startActivity(intent);
     }
}