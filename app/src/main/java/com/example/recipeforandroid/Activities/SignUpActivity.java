package com.example.recipeforandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.recipeforandroid.Network.NetworkCallback;
import com.example.recipeforandroid.Network.NetworkManager;
import com.example.recipeforandroid.R;
import com.example.recipeforandroid.Services.UserService;
import com.google.android.material.button.MaterialButton;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

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
                NetworkManager netw = new NetworkManager(getApplicationContext());
                String legalUsername = "";
                String legalPassword = "";
                if (UserService.isLegalUsername(username.getText().toString()) && UserService.isLegalPassword(password.getText().toString())) {
                    legalUsername = username.getText().toString();
                    legalPassword = password.getText().toString();
                }
                netw.signUp(legalUsername, legalPassword, new NetworkCallback() {
                    @Override
                    public void onSuccess(Object result) {
                        goToSignIn();
                    }

                    @Override
                    public void onFailure(String errorString) {
                        Log.d(TAG, "signUp errorString:" +errorString);
                        if (!UserService.isLegalUsername(username.getText().toString())) {
                            Toast.makeText(SignUpActivity.this, R.string.username_password_illegal_toast, Toast.LENGTH_SHORT).show();
                        }

                        else if (!UserService.isLegalUsername(username.getText().toString())) {
                            Toast.makeText(SignUpActivity.this, R.string.username_illegal_toast, Toast.LENGTH_SHORT).show();
                        }
                        else if (!UserService.isLegalUsername(password.getText().toString())) {
                            Toast.makeText(SignUpActivity.this, R.string.password_illegal_toast, Toast.LENGTH_SHORT).show();
                        }
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