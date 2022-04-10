package com.example.recipeforandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class SignInActivity extends AppCompatActivity {

    private SharedPreferences mSp;

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
                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                    //correct
                    mSp.edit().putBoolean("logged", true).apply();
                    mSp.edit().putString("user", username.getText().toString()).apply();
                    goToRecipeList();
                } else
                    //incorrect
                    Toast.makeText(SignInActivity.this, R.string.login_failed_toast, Toast.LENGTH_SHORT).show();
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
         startActivity(intent);
     }
}