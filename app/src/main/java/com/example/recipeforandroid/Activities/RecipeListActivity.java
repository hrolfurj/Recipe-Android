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

import com.example.recipeforandroid.AboutUs;
import com.example.recipeforandroid.Settings;
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

public class RecipeListActivity extends AppCompatActivity implements RecycleViewInterface{

    private static final String TAG = "Recipe Book";
    Menu menu;

    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Recipe> recipeList2;
    private RecyclerView.LayoutManager layoutManager;
    private RecycleViewAdapter adapter2;
    private SharedPreferences mSp;

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
        mSp = getSharedPreferences("login", MODE_PRIVATE);


        drawerLayout = findViewById(R.id.drawer_layout);

        SharedPreferences userSp = getSharedPreferences("login", MODE_PRIVATE);
        String userName = userSp.getString("user", "null");
        Log.d(TAG, "onCreate: " + "; userName: " + userName);

        long id = mSp.getLong("userID", 0);

        NetworkManager netw = new NetworkManager(getApplicationContext());
        netw.getUserRecipes(id, new NetworkCallback() {
            @Override
            public void onSuccess(Object result) {
                // TODO breyta Object result í ArrayList<Recipe> ?
                System.out.println("Object result: " + result.toString());
                System.out.println("GREAT SUCCESS!");
                fillRecipeList2((JSONObject) result);
            }
            @Override
            public void onFailure(String errorString) {
                System.out.println("FAIL!");
            }
        });

        Button addRecipeButton = (Button) findViewById(R.id.add_recipe_button);
        Button logoutButton = (Button) findViewById(R.id.Logout_button);

        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);

        TextView welcome = (TextView) findViewById(R.id.welcome_text);
        welcome.setText("Welcome " +sp.getString("user", "null") + "!");

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
                sp.edit().putBoolean("logged", false).apply();
                startActivity(intent);
            }
        });

        Button deleteUserButton = (Button) findViewById(R.id.delete_account);
        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long id = mSp.getLong("userID", 0);
                NetworkManager netw = new NetworkManager(getApplicationContext());
                netw.deleteUser(id, new NetworkCallback() {
                    @Override
                    public void onSuccess(Object result) {
                        sp.edit().putBoolean("logged", false).apply();
                        Intent intent = new Intent(RecipeListActivity.this, SignInActivity.class);
                        startActivity(intent);
                        Toast.makeText(RecipeListActivity.this, "User deleted", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(String errorString) {
                        Toast.makeText(RecipeListActivity.this, "User failed to delete..", Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });
    }


    public void ClickMenu (View view){
        //open drawer
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        //open drawer layout
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        //close drawer
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        //close drawer layout
        //check condition
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            //When drawer is open
            //Close drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view){
        //Recreate activity
        recreate();
    }

    public void ClickDashboard(View view){
        //Redirect activity to dashboard
        redirectActivity(this, Settings.class);
    }

    public void ClickAboutUs(View view){
        //Redicect activity to about us
        redirectActivity(this, AboutUs.class);
    }

    public void ClickLogout(View view){
        //Close app
        RecipeListActivity.exit(this);
    }


    public static void exit(Activity activity){

        //Initialize alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        //Set title
        builder.setTitle("Exit");
        //
        builder.setMessage("Are you sure you want to exit the app " + "?");
        //Positive yes button
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Finish activity
                activity.finishAffinity();
                //Exit app
                System.exit(0);

            }
        });

        //Negative no button
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Dismiss dialog
                dialog.dismiss();
            }
        });
        //show dialog
        builder.show();

    }

    public static void redirectActivity(Activity activity, Class aClass) {
        //Initialize intent
        Intent intent = new Intent(activity,aClass);
        //
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    protected void onPause(){
        super.onPause();
        //close drawer
        closeDrawer(drawerLayout);
    }

    String deletedRecipe = null;
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if(viewHolder instanceof RecycleViewAdapter.MyViewHolder);
            String nameRecipeDelete = recipeList2.get(viewHolder.getAbsoluteAdapterPosition()).getRecipeTitle();
            final Recipe recipeDelete = recipeList2.get(viewHolder.getAbsoluteAdapterPosition());
            final int indexDelete = viewHolder.getAbsoluteAdapterPosition();
            //deleteRecipeFromList(recipeDelete);
            Timer time = new Timer();
            time.schedule(new TimerTask() {
                @Override
                public void run() {
                    deleteRecipeFromList(recipeDelete);
                }
            }, 8000);

            adapter2.removeItem(indexDelete);

            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Removed", Snackbar.LENGTH_LONG);
            snackbar.setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    time.cancel();

                    // smá galli, recipe birtir sömu uppskrift 2x
                    recipeList2.add(indexDelete, recipeDelete);
                   /* adapter.restoreRecipe(recipeDelete, indexDelete);
                    recyclerView.scrollToPosition(indexDelete);*/
                    //adapter2.notifyItemInserted(indexDelete);
                    //restoreRecipe(recipeDelete);
                    adapter2.notifyItemInserted(indexDelete);
                }
            });
            snackbar.setTextColor(Color.BLACK);
            snackbar.setActionTextColor(Color.BLACK);
            snackbar.setBackgroundTint(getResources().getColor(R.color.recipe_green));
            snackbar.setDuration(7000).show();
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

    private void fillRecipeList2(JSONObject recipeList) {
        Gson gson = new Gson();
        Recipe recipe = new Recipe();
        /**try {
            recipeList.get("recipes");
        } catch (JSONException e) {
            e.printStackTrace();
        }**/
        //RecipeResponse temp = new RecipeResponse();
        /**Type RecipeResponse = new TypeToken<List<Recipe2>>(){}.getType();
        List<Recipe2> recipe2List = gson.fromJson(recipeList, RecipeResponse);**/

        Recipe[] arrayOfRecipes = new Recipe[1];
        try {
            arrayOfRecipes = gson.fromJson(String.valueOf(recipeList.get("recipes")), Recipe[].class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        recipeList2 = new ArrayList<Recipe>();

        for (int i = 0; i < arrayOfRecipes.length; i++) {
            recipeList2.add(arrayOfRecipes[i]);
        }
        setUpRecyclerView2(recipeList2);
    }

    private void setUpRecyclerView2(List<Recipe> recipeList) {
        RecyclerView recyclerView = findViewById(R.id.lv_recipeList);
        recyclerView.setHasFixedSize(true);
        adapter2 = new RecycleViewAdapter(recipeList, RecipeListActivity.this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter2);
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
                if (adapter2 != null){
                    adapter2.getFilter().filter(newText);
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
                // sort a to z
                Collections.sort(recipeList2, RecipeService.RecipeTitleAZComparator);
                Toast.makeText(RecipeListActivity.this, "Sort A to Z", Toast.LENGTH_SHORT).show();
                adapter2.notifyDataSetChanged();
                return true;
            case R.id.menu_ZA:
                // sort z to a
                Collections.sort(recipeList2,RecipeService.RecipeTitleZAComparator);
                Toast.makeText(RecipeListActivity.this, "Sort Z to A", Toast.LENGTH_SHORT).show();
                adapter2.notifyDataSetChanged();
                return true;
            case R.id.menu_NewOld:
                // sort a to z
                Collections.sort(recipeList2, RecipeService.RecipeNewOldComparator);
                Toast.makeText(RecipeListActivity.this, "Sort A to Z", Toast.LENGTH_SHORT).show();
                adapter2.notifyDataSetChanged();
                return true;
            case R.id.menu_OldNew:
                // sort z to a
                Collections.sort(recipeList2,RecipeService.RecipeOldNewComparator);
                Toast.makeText(RecipeListActivity.this, "Sort Z to A", Toast.LENGTH_SHORT).show();
                adapter2.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(RecipeListActivity.this, ViewRecipeActivity.class);
        intent.putExtra("Title", recipeList2.get(position).getRecipeTitle());
        intent.putExtra("Tag", recipeList2.get(position).getRecipeTag());
        intent.putExtra("Description", recipeList2.get(position).getRecipeText());
        intent.putExtra("Image", recipeList2.get(position).getRecipeImage());
        intent.putExtra("RecipeID", recipeList2.get(position).getID());
        startActivity(intent);
    }
    public void deleteRecipeFromList (Recipe recipe) {
        NetworkManager netw = new NetworkManager(getApplicationContext());
        netw.deleteRecipe(recipe, new NetworkCallback() {
            @Override
            public void onSuccess(Object result) {
                System.out.println("Uppskrift eytt");
            }

            @Override
            public void onFailure(String errorString) {
                System.out.println(errorString);
            }
        });
    }

    public void restoreRecipe (Recipe recipe) {
        NetworkManager netw = new NetworkManager(getApplicationContext());
        netw.saveRecipe(recipe, new NetworkCallback() {
            @Override
            public void onSuccess(Object result) {
                System.out.println("Uppskrift bætt aftur í lista");
            }

            @Override
            public void onFailure(String errorString) {
                System.out.println(errorString);
            }
        });
    }
}