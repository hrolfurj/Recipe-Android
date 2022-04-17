package com.example.recipeforandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

import com.example.recipeforandroid.Activities.RecipeListActivity;

public class AboutUs extends AppCompatActivity {
    //Initialize variable
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

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
        //Redirect activity to dashboard
        RecipeListActivity.redirectActivity(this,Dashboard.class);
    }

    public void ClickAboutUs(View view){
        //Recreate activity
        recreate();

    }

    public void ClickLogout(View view){
        //Close app
        RecipeListActivity.logout(this);
    }

    @Override
    protected void onPause(){
        super.onPause();
        //close drawer
        RecipeListActivity.closeDrawer(drawerLayout);
    }
}