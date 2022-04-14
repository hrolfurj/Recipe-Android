package com.example.recipeforandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipeforandroid.Network.NetworkCallback;
import com.example.recipeforandroid.Network.NetworkManager2;
import com.example.recipeforandroid.R;
import com.google.android.material.button.MaterialButton;

public class SignUpActivity extends AppCompatActivity {


    /**
     * TODO: PLACEHOLDER, work in progress.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        EditText username = (EditText) findViewById(R.id.username);
        EditText password = (EditText) findViewById(R.id.password);
        TextView login = (TextView) findViewById(R.id.login_hyperlink_text);

        MaterialButton register_button = (MaterialButton) findViewById(R.id.register_button);

        register_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                NetworkManager2 netw = new NetworkManager2(getApplicationContext());
                netw.signUp(username.getText().toString(), password.getText().toString(), new NetworkCallback() {
                    @Override
                    public void onSuccess(Object result) {
                        goToSignIn();
                    }

                    @Override
                    public void onFailure(String errorString) {
                        System.out.println(errorString);
                    }
                });
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignIn();
            }
        });
    }
    public  void  goToSignIn() {
        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
        startActivity(intent);
    }
}