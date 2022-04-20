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

public class ChangePassword extends AppCompatActivity {
    //Initialize variable
    DrawerLayout drawerLayout;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        Button mChangePasswordButton = (Button) findViewById(R.id.change_password_button);
        TextView mConfirmOldPassword = (TextView) findViewById(R.id.old_password);
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
                netw.isLoggedIn(userName, mConfirmOldPassword.getText().toString(), new NetworkCallback() {
                    @Override
                    public void onSuccess(Object result) {
                        if (mNewPassword.getText().toString().compareTo(mConfirmNewPassword.getText().toString()) == 0) {
                            netw.changePassword(userName, mNewPassword.getText().toString(), new NetworkCallback() {
                                @Override
                                public void onSuccess(Object result) {
                                    Intent intent = new Intent(ChangePassword.this, RecipeListActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(String errorString) {
                                    System.out.println("ChangePasswordActivity - Something went wrong");
                                }
                            });
                        }

                    }
                    @Override
                    public void onFailure(String errorString) {
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