package com.example.recipeforandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipeforandroid.Activities.RecipeListActivity;
import com.example.recipeforandroid.Activities.SignInActivity;
import com.example.recipeforandroid.Network.NetworkCallback;
import com.example.recipeforandroid.Network.NetworkManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePassword extends AppCompatActivity {
    //Initialize variable
    DrawerLayout drawerLayout;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Button mChangePasswordButton = (Button) findViewById(R.id.change_password_button);
        TextView mOldPassword = (TextView) findViewById(R.id.old_password);
        TextView mNewPassword = (TextView) findViewById(R.id.new_password);
        TextView mConfirmNewPassword = (TextView) findViewById(R.id.confirm_new_password);

        sp = getSharedPreferences("login", MODE_PRIVATE);
        String userName = sp.getString("user", "null");
        long userID = sp.getLong("userID", 0);


        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);

        mChangePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NetworkManager netw = new NetworkManager(getApplicationContext());
                Pattern p = Pattern.compile("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");
                Matcher m = p.matcher(mNewPassword.getText().toString());
                Matcher n = p.matcher(mConfirmNewPassword.getText().toString());
                boolean legalPasswords = m.matches() && n.matches();
                netw.isLoggedIn(userName, mOldPassword.getText().toString(), new NetworkCallback() {
                    @Override
                    public void onSuccess(Object result) {
                        if (mNewPassword.getText().toString().compareTo(mConfirmNewPassword.getText().toString()) == 0 && legalPasswords == true) {
                            netw.changePassword(userName, mConfirmNewPassword.getText().toString(), new NetworkCallback() {
                                @Override
                                public void onSuccess(Object result) {
                                    Intent intent = new Intent(ChangePassword.this, RecipeListActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(String errorString) {
                                    mNewPassword.setText("");
                                    mConfirmNewPassword.setText("");
                                    Toast.makeText(ChangePassword.this, R.string.password_change_fail_toast, Toast.LENGTH_SHORT).show();
                                    System.out.println("ChangePasswordActivity - Something went wrong");
                                }
                            });
                        }
                        else {
                            mNewPassword.setText("");
                            mConfirmNewPassword.setText("");
                            if (legalPasswords) {
                                Toast.makeText(ChangePassword.this, R.string.passwords_no_match_toast, Toast.LENGTH_SHORT).show();
                            }
                            else Toast.makeText(ChangePassword.this, R.string.password_illegal_toast, Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onFailure(String errorString) {
                        mOldPassword.setText("");
                        Toast.makeText(ChangePassword.this, R.string.password_wrong_toast, Toast.LENGTH_SHORT).show();
                        System.out.println("Something went wrong");

                    }
                });
            }
        });
    }
    public void ClickMenu(View view){
        //Open drawer
        RecipeListActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view){
        //Close drawer
        RecipeListActivity.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view){
        //Redirect activity to home
        RecipeListActivity.redirectActivity(this, RecipeListActivity.class);
    }

    public void ClickDashboard(View view){
        //Redirect activity to dashboard
        RecipeListActivity.redirectActivity(this, Settings.class);
    }

    public void ClickAboutUs(View view){
        //Redirect activity to about us
        RecipeListActivity.redirectActivity(this,AboutUs.class);
    }

    public void ClickDeleteAccount (View view) {
        RecipeListActivity.redirectActivity(this, DeleteUser.class);
    }

    public void ClickChangePassword (View view) {
        recreate();
    }

    public void ClickLogout(View view){
        //Close app
        RecipeListActivity.exit(this);
    }



    @Override
    protected void onPause(){
        super.onPause();
        //close drawer
        RecipeListActivity.closeDrawer(drawerLayout);
    }
}