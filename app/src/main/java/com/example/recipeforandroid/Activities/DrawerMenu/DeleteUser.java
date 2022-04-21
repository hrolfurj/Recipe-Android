package com.example.recipeforandroid.Activities.DrawerMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipeforandroid.Activities.RecipeListActivity;
import com.example.recipeforandroid.Activities.SignInActivity;
import com.example.recipeforandroid.Network.NetworkCallback;
import com.example.recipeforandroid.Network.NetworkManager;
import com.example.recipeforandroid.R;

public class DeleteUser extends AppCompatActivity {
    //Initialize variable
    DrawerLayout drawerLayout;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        Button mConfirmDeleteUserButton = (Button) findViewById(R.id.delete_user_button);
        TextView mConfirmPassword = (TextView) findViewById(R.id.confirm_password);

        sp = getSharedPreferences("login", MODE_PRIVATE);
        String userName = sp.getString("user", "null");
        long userID = sp.getLong("userID", 0);


        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);

        mConfirmDeleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = mConfirmPassword.getText().toString();
                NetworkManager netw = new NetworkManager(getApplicationContext());
                netw.isLoggedIn(userName, mConfirmPassword.getText().toString(), new NetworkCallback() {
                    @Override
                    public void onSuccess(Object result) {
                        sp.edit().putBoolean("logged", false).apply();
                        netw.deleteUser(userID, new NetworkCallback() {
                            @Override
                            public void onSuccess(Object result) {
                                Intent intent = new Intent(DeleteUser.this, SignInActivity.class);
                                startActivity(intent);
                                Toast.makeText(DeleteUser.this, "User deleted", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onFailure(String errorString) {
                                Toast.makeText(DeleteUser.this, "User failed to delete..", Toast.LENGTH_SHORT).show();

                            }
                        });

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
        recreate();
    }

    public void ClickChangePassword (View view) {
        RecipeListActivity.redirectActivity(this, ChangePassword.class);
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