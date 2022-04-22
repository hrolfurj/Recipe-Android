package com.example.recipeforandroid.Activities.DrawerMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import android.view.View;
import com.example.recipeforandroid.Activities.RecipeListActivity;
import com.example.recipeforandroid.R;

public class AboutUs extends AppCompatActivity {
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        drawerLayout = findViewById(R.id.drawer_layout);
    }

    public void ClickMenu(View view){
        RecipeListActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view){
        RecipeListActivity.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view){
        RecipeListActivity.redirectActivity(this, RecipeListActivity.class);
    }

    public void ClickDashboard(View view){
        RecipeListActivity.redirectActivity(this, Settings.class);
    }

    public void ClickAboutUs(View view){
        recreate();
    }

    public void ClickDeleteAccount (View view) {
        RecipeListActivity.redirectActivity(this, DeleteUser.class);
    }

    public void ClickChangePassword (View view) {
        RecipeListActivity.redirectActivity(this, ChangePassword.class);
    }

    public void ClickLogout(View view){
        RecipeListActivity.exit(this);
    }

    @Override
    protected void onPause(){
        super.onPause();
        RecipeListActivity.closeDrawer(drawerLayout);
    }

}