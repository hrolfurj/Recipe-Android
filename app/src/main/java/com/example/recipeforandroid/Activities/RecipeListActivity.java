package com.example.recipeforandroid.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipeforandroid.Activities.DrawerMenu.AboutUs;
import com.example.recipeforandroid.Activities.DrawerMenu.ChangePassword;
import com.example.recipeforandroid.Activities.DrawerMenu.DeleteUser;
import com.example.recipeforandroid.Activities.DrawerMenu.Settings;
import com.example.recipeforandroid.Activities.RecyclerAdapter.RecycleViewAdapter;
import com.example.recipeforandroid.Activities.RecyclerAdapter.RecycleViewInterface;
import com.example.recipeforandroid.Network.NetworkCallback;
import com.example.recipeforandroid.Network.NetworkManager;
import com.example.recipeforandroid.Persistence.Entities.Recipe;
import com.example.recipeforandroid.R;
import com.example.recipeforandroid.Services.RecipeService;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class RecipeListActivity extends AppCompatActivity implements RecycleViewInterface {

    private static final String TAG = "RecipeListActivity";
    private static final int DELETE_DELAY = 5100;
    private static final Integer UNDO_DELAY = 5000;
    private static final String WELCOME_TEXT = "Welcome ";
    private List<Recipe> mRecipeList;
    private RecycleViewAdapter mAdapter;
    private SharedPreferences mSP;

    DrawerLayout drawerLayout;

    /**
     * Uppskriftirnar eru ekki tengdar við gagnagrunn, en eru notaðar sem mock-object eins og er.
     * TODO: Eftir að bæta við "search", "favorites" og "sort".
     * RecycleView listinn styðst við recipes og RecycleViewAdapter til að birta listann.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSP = getSharedPreferences("login", MODE_PRIVATE);
        Button addRecipeButton = (Button) findViewById(R.id.add_recipe_button);
        Button logoutButton = (Button) findViewById(R.id.Logout_button);
        drawerLayout = findViewById(R.id.drawer_layout);
        long id = mSP.getLong("userID", 0);

        NetworkManager netw = new NetworkManager(getApplicationContext());
        netw.getUserRecipes(id, new NetworkCallback() {
            @Override
            public void onSuccess(Object result) {
                fillRecipeList((JSONObject) result);
            }
            @Override
            public void onFailure(String errorString) {
                Log.d(TAG, "errorString:" +errorString);
            }
        });

        TextView welcome = (TextView) findViewById(R.id.welcome_text);
        welcome.setText(WELCOME_TEXT + mSP.getString("user", "null") + "!");

        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeListActivity.this, NewRecipeActivity.class);
                intent.putExtra("Title", "");
                intent.putExtra("Tag", "");
                intent.putExtra("Description", "");
                intent.putExtra("Image", "");
                intent.putExtra("RecipeID", 0);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeListActivity.this, SignInActivity.class);
                mSP.edit().putBoolean("logged", false).apply();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    public void ClickMenu (View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view){
        recreate();
    }

    public void ClickDashboard(View view){
        redirectActivity(this, Settings.class);
    }

    public void ClickAboutUs(View view){
        redirectActivity(this, AboutUs.class);
    }

    public void ClickLogout(View view){
        RecipeListActivity.exit(this);
    }

    public void ClickDeleteAccount (View view) {
        redirectActivity(this, DeleteUser.class);
    }

    public void ClickChangePassword (View view) {
        RecipeListActivity.redirectActivity(this, ChangePassword.class);
    }

    public static void exit(Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure you want to exit the app " + "?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finishAffinity();
                System.exit(0);
            }
        });

        //Negative no button
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity,aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    protected void onPause(){
        super.onPause();
        closeDrawer(drawerLayout);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @SuppressLint("WrongConstant")
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if(viewHolder instanceof RecycleViewAdapter.MyViewHolder);
            final Recipe recipeDelete = mRecipeList.get(viewHolder.getAbsoluteAdapterPosition());
            final int indexDelete = viewHolder.getAbsoluteAdapterPosition();
            Timer time = new Timer();
            time.schedule(new TimerTask() {
                @Override
                public void run() {
                    RecipeService.deleteRecipeFromList (recipeDelete, getApplicationContext());
                }
            }, DELETE_DELAY);

            mAdapter.removeItem(indexDelete);

            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Removed", Snackbar.LENGTH_LONG);
            snackbar.setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    time.cancel();
                    mRecipeList.add(indexDelete, recipeDelete);
                    mAdapter.notifyItemInserted(indexDelete);
                }
            });
            snackbar.setTextColor(Color.BLACK);
            snackbar.setActionTextColor(Color.BLACK);
            snackbar.setBackgroundTint(getResources().getColor(R.color.recipe_green));
            snackbar.setDuration(UNDO_DELAY).show();
            snackbar.show();
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(RecipeListActivity.this, R.color.red))
                    .addActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    private void fillRecipeList(JSONObject recipeList) {
        Gson gson = new Gson();
        Recipe[] arrayOfRecipes = new Recipe[1];
        try {
            arrayOfRecipes = gson.fromJson(String.valueOf(recipeList.get("recipes")), Recipe[].class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.mRecipeList = new ArrayList<>();

        for (int i = 0; i < arrayOfRecipes.length; i++) {
            this.mRecipeList.add(arrayOfRecipes[i]);
        }
        setUpRecyclerView(this.mRecipeList);
    }

    private void setUpRecyclerView(List<Recipe> recipeList) {
        RecyclerView recyclerView = findViewById(R.id.lv_recipeList);
        recyclerView.setHasFixedSize(true);
        mAdapter = new RecycleViewAdapter(recipeList, RecipeListActivity.this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (mAdapter != null){
                    mAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_AZ:
                Collections.sort(mRecipeList, RecipeService.RecipeTitleAZComparator);
                Toast.makeText(RecipeListActivity.this, R.string.sort_az, Toast.LENGTH_SHORT).show();
                mAdapter.notifyDataSetChanged();
                return true;
            case R.id.menu_ZA:
                Collections.sort(mRecipeList,RecipeService.RecipeTitleZAComparator);
                Toast.makeText(RecipeListActivity.this, R.string.sort_za, Toast.LENGTH_SHORT).show();
                mAdapter.notifyDataSetChanged();
                return true;
            case R.id.menu_NewOld:
                Collections.sort(mRecipeList, RecipeService.RecipeNewOldComparator);
                Toast.makeText(RecipeListActivity.this, R.string.sort_new_old, Toast.LENGTH_SHORT).show();
                mAdapter.notifyDataSetChanged();
                return true;
            case R.id.menu_OldNew:
                Collections.sort(mRecipeList,RecipeService.RecipeOldNewComparator);
                Toast.makeText(RecipeListActivity.this, R.string.sort_old_new, Toast.LENGTH_SHORT).show();
                mAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(RecipeListActivity.this, ViewRecipeActivity.class);
        intent.putExtra("Title", mRecipeList.get(position).getRecipeTitle());
        intent.putExtra("Tag", mRecipeList.get(position).getRecipeTag());
        intent.putExtra("Description", mRecipeList.get(position).getRecipeText());
        intent.putExtra("Image", mRecipeList.get(position).getRecipeImage());
        intent.putExtra("RecipeID", mRecipeList.get(position).getID());
        startActivity(intent);
    }
}