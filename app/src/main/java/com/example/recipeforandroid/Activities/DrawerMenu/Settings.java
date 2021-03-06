package com.example.recipeforandroid.Activities.DrawerMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import android.view.View;
import com.example.recipeforandroid.Activities.RecipeListActivity;
import com.example.recipeforandroid.R;

public class Settings extends AppCompatActivity {
    //initialize variable
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //Assign variable
        drawerLayout = findViewById(R.id.drawer_layout);
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
        //Recreate activity
        recreate();
    }

    public void ClickAboutUs(View view){
        //Redirect activity to about us
        RecipeListActivity.redirectActivity(this,AboutUs.class);
    }

    public void ClickDeleteAccount (View view) {
        RecipeListActivity.redirectActivity(this, DeleteUser.class);
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