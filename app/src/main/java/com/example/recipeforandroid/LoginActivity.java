package com.example.recipeforandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);
        TextView register = (TextView) findViewById(R.id.register_hyperlink_text);


        MaterialButton login_button = (MaterialButton) findViewById(R.id.login_button);

        sp = getSharedPreferences("login", MODE_PRIVATE);

        if(sp.getBoolean("logged", false)){
            goToRecipeList();
        }

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                    //correct
                    goToRecipeList();
                    sp.edit().putBoolean("logged", true).apply();
                } else
                    //incorrect
                    Toast.makeText(LoginActivity.this, R.string.login_failed_toast, Toast.LENGTH_SHORT).show();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
     public  void  goToRecipeList() {
         Intent intent = new Intent(LoginActivity.this, RecipeListActivity.class);
         startActivity(intent);
     }
}