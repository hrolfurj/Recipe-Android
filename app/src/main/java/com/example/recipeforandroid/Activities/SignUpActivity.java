package com.example.recipeforandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.recipeforandroid.Network.NetworkCallback;
import com.example.recipeforandroid.Network.NetworkManager;
import com.example.recipeforandroid.R;
import com.google.android.material.button.MaterialButton;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

                NetworkManager netw = new NetworkManager(getApplicationContext());
                Pattern p = Pattern.compile("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");
                Matcher m = p.matcher(username.getText().toString());
                Matcher n = p.matcher(password.getText().toString());
                boolean b = m.matches() && n.matches();
                String verifiedUsername = "";
                String verifiedPassword = "";
                if (b == true) {
                    verifiedUsername = username.getText().toString();
                    verifiedPassword = password.getText().toString();
                }
                netw.signUp(verifiedUsername, verifiedPassword, new NetworkCallback() {
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