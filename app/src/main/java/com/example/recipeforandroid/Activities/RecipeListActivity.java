package com.example.recipeforandroid.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
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
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recipeforandroid.Network.NetworkCallback;
import com.example.recipeforandroid.Network.NetworkManager;
import com.example.recipeforandroid.Network.NetworkManager2;
import com.example.recipeforandroid.Persistence.Entities.Recipe;
import com.example.recipeforandroid.Persistence.Entities.Recipe2;
import com.example.recipeforandroid.Persistence.Entities.RecipeResponse;
import com.example.recipeforandroid.R;
import com.example.recipeforandroid.Services.RecipeService;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class RecipeListActivity extends AppCompatActivity implements RecycleViewInterface{

    private static final String TAG = "Recipe Book";
    Menu menu;




  /*  List<Recipe> recipeList = new ArrayList<Recipe>(); */

    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Recipe> recipeList;
    private List<Recipe2> recipeList2;
    private RecyclerView.LayoutManager layoutManager;
    //private RecycleViewAdapter adapter;
    private RecycleViewAdapter2 adapter2;
    private SharedPreferences mSp;
    MaterialToolbar mToolbar;
    DrawerLayout mDrawerLayout;
    FrameLayout mFrameLayout;

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

        // mToolbar = findViewById(R.id.Toolbar);

        // mFrameLayout = findViewById(R.id.drawer_layout);
        //mDrawerLayout = findViewById(R.id.drawer_layout);

        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_open, R.string.navigation_close);
        //mDrawerLayout.addDrawerListener(toggle);
        //toggle.syncState();



        SharedPreferences userSp = getSharedPreferences("login", MODE_PRIVATE);
        String userName = userSp.getString("user", "null");
        Log.d(TAG, "onCreate: " + "; userName: " + userName);



        //fillrecipeList();
        //setUpRecyclerView();


        /*
        
        NetworkManager networkManager = NetworkManager.getInstance(this);
        networkManager.getRecipes(new NetworkCallback<List<Recipe>>() {
            @Override
            public void onSuccess(List<Recipe> result) {
                recipeList = result;
                Log.d(TAG, "First recipe in list: " + recipeList.get(0).getTitle());
            }

            @Override
            public void onFailure(String errorString) {

            }

        });*/
        long id = mSp.getLong("userID", 0);



        NetworkManager2 netw = new NetworkManager2(getApplicationContext());
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
        //Log.d(TAG, "First recipe in list: " + recipeList.get(0).getTitle());

        /*
        NetworkManager networkManager = NetworkManager.getInstance(this);
        networkManager.userRecipeList(userName, new NetworkCallback<List<Recipe>>() {
            @Override
            public void onSuccess(List<Recipe> result) {
                recipeList = result;
                Log.d(TAG, "First recipe in list: " + recipeList.get(1).getTitle());

            }

            @Override
            public void onFailure(String errorString) {
                Log.d(TAG, "onFailure: " + errorString.toString());

            }
        });
         */



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
    }
/**
    private void setUpRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.lv_recipeList);
        recyclerView.setHasFixedSize(true);

        // RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new RecycleViewAdapter(recipeList, RecipeListActivity.this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);

        /*
        adapter = new RecycleViewAdapter(this,lstAnime) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }**/
    String deletedRecipe = null;
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if(viewHolder instanceof RecycleViewAdapter2.MyViewHolder);
            String nameRecipeDelete = recipeList2.get(viewHolder.getAbsoluteAdapterPosition()).getRecipeTitle();

         /* recipeList.remove(viewHolder.getAdapterPosition());
            adapter.notifyDataSetChanged(); */

            final Recipe2 recipeDelete = recipeList2.get(viewHolder.getAbsoluteAdapterPosition());
            final int indexDelete = viewHolder.getAbsoluteAdapterPosition();

            adapter2.removeItem(indexDelete);
            //adapter2.notifyItemRemoved(indexDelete);

            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Removed", Snackbar.LENGTH_LONG);
            snackbar.setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // smá galli, recipe birtir sömu uppskrift 2x
                    recipeList2.add(indexDelete, recipeDelete);
                   /* adapter.restoreRecipe(recipeDelete, indexDelete);
                    recyclerView.scrollToPosition(indexDelete);*/
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
/**
    private void fillrecipeList() {
        recipeList = new ArrayList<>();
        recipeList.add(new Recipe(0,"Pizza Mamaria", "Pizza", "Hveiti, vatn, olía...", "data:image/jpeg;base64,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"));
        recipeList.add(new Recipe(1,"Carbonara", "Pasta", "Hveiti, vatn...", ""));
        recipeList.add(new Recipe(2,"Chili Con Carne", "Hakk", "Hveiti, vatn...", ""));
        recipeList.add( new Recipe(3,"Spaghetti & Meatballs", "Pasta", "Hveiti, vatn...", "data:image/jpeg;base64,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"));
        recipeList.add( new Recipe(4,"Plokkfiskur", "Fiskur", "Hveiti, vatn...", ""));
        recipeList.add( new Recipe(5,"Lasagna", "Pasta", "Hveiti, vatn...", ""));

        /*
        NetworkManager networkManager = new NetworkManager(getApplicationContext());
        networkManager.userRecipeList(userName, new NetworkCallback<List<Recipe>>() {
            @Override
            public void onSuccess(List<Recipe> result) {
                recipeList = result;

            }

            @Override
            public void onFailure(String errorString) {

            }
        });


    } **/

    private void fillRecipeList2(JSONObject recipeList) {
        Gson gson = new Gson();
        Recipe2 recipe = new Recipe2();
        /**try {
            recipeList.get("recipes");
        } catch (JSONException e) {
            e.printStackTrace();
        }**/
        //RecipeResponse temp = new RecipeResponse();
        /**Type RecipeResponse = new TypeToken<List<Recipe2>>(){}.getType();
        List<Recipe2> recipe2List = gson.fromJson(recipeList, RecipeResponse);**/

        Recipe2[] arrayOfRecipes = new Recipe2[1];
        try {
            arrayOfRecipes = gson.fromJson(String.valueOf(recipeList.get("recipes")), Recipe2[].class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        recipeList2 = new ArrayList<Recipe2>();

        for (int i = 0; i < arrayOfRecipes.length; i++) {
            recipeList2.add(arrayOfRecipes[i]);
        }

        //recipeList2.add(arrayOfRecipes[0]);
        //recipeList2.add(arrayOfRecipes[1]);
        System.out.println("ggggggggggggggg");
        setUpRecyclerView2(recipeList2);
    }

    private void setUpRecyclerView2(List<Recipe2> recipeList) {
        RecyclerView recyclerView = findViewById(R.id.lv_recipeList);
        recyclerView.setHasFixedSize(true);

        // RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter2 = new RecycleViewAdapter2(recipeList, RecipeListActivity.this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter2);

        /*
        adapter = new RecycleViewAdapter(this,lstAnime) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        */

    }


    /**
     * Mock-objects.
     * TODO: verður fjarlægt þegar við tengjum við gagnagrunnin.
     */


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
/**
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_AZ:
            // sort a to z
                Collections.sort(recipeList,Recipe.RecipeTitleAZComparator);
                Toast.makeText(RecipeListActivity.this, "Sort A to Z", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
                return true;
            case R.id.menu_ZA:
                // sort z to a
                Collections.sort(recipeList,Recipe.RecipeTitleZAComparator);
                Toast.makeText(RecipeListActivity.this, "Sort Z to A", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
**/
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(RecipeListActivity.this, ViewRecipeActivity.class);
/**
        intent.putExtra("Title", recipeList.get(position).getTitle());
        intent.putExtra("Tag", recipeList.get(position).getTag());
        intent.putExtra("Description", recipeList.get(position).getDescription());
        intent.putExtra("Image", recipeList.get(position).getUploadImage());
**/
        intent.putExtra("Title", recipeList2.get(position).getRecipeTitle());
        //System.out.println("recipeList2 getTitle: " +recipeList2.g);
        intent.putExtra("Tag", recipeList2.get(position).getRecipeTag());
        intent.putExtra("Description", recipeList2.get(position).getRecipeText());
        intent.putExtra("Image", recipeList2.get(position).getRecipeImagePath());
        intent.putExtra("RecipeID", recipeList2.get(position).getID());
        System.out.println("recipeList2 getID: " +recipeList2.get(position).getID());
        startActivity(intent);

    }
}