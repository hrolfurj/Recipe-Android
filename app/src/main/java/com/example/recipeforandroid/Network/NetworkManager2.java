package com.example.recipeforandroid.Network;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.recipeforandroid.Persistence.Entities.Recipe;
import com.example.recipeforandroid.Persistence.Entities.Recipe2;
import com.example.recipeforandroid.Persistence.Entities.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.xml.transform.ErrorListener;

public class NetworkManager2 extends Application{

    private Context mContext;
    private static final String BASE_URL = "http://10.0.2.2:8080/";
    JSONObject userLogin = new JSONObject();

    RequestQueue queue;
    private SharedPreferences mSp;

    public static final String TAG = NetworkManager.class
            .getSimpleName();

    public NetworkManager2(Context context) {
        mContext = context;
    }

    public void login(String username, String password, final NetworkCallback callBack) {
        queue = Volley.newRequestQueue(mContext);
        try {
            userLogin.put("username", username);
            userLogin.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BASE_URL + "api/login", userLogin, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callBack.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callBack.onFailure(error.toString());
            }
        });
        queue.add(request);
    }

    public void signUp(String username, String password, final NetworkCallback callback) {
        queue = Volley.newRequestQueue(mContext);
        try {
            userLogin.put("username", username);
            userLogin.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BASE_URL + "api/signUp", userLogin, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        });
        queue.add(request);
    }

    public void getUserRecipes(long id, final NetworkCallback callback) {
        queue = Volley.newRequestQueue(mContext);
        JsonObjectRequest request = new JsonObjectRequest(BASE_URL +"api/" + id + "/recipeList", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("ÆDÍIÐ ER: " +id);
                Gson gson = new Gson();

                // TODO: Athuga hvað kemur útúr þessu, laga
                Recipe recipe = gson.fromJson(String.valueOf(response), Recipe.class);
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure((error.toString()));
            }
        });
        queue.add(request);
    }

    public void saveRecipe(Recipe2 recipe, final NetworkCallback callback) {
        queue = Volley.newRequestQueue((mContext));
        Gson gson = new Gson();
        //JSONObject jsonRecipe = gson.toJson(recipe, Recipe2.class);
        String temp = gson.toJson(recipe);
        JSONObject jsonRecipe = new JSONObject();

        try {
            jsonRecipe.put("userID", recipe.getUserID());
            jsonRecipe.put("recipeTitle", recipe.getRecipeTitle());
            jsonRecipe.put("recipeText", recipe.getRecipeText());
            jsonRecipe.put("recipeTag", recipe.getRecipeTag());
            jsonRecipe.put("id", recipe.getID());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BASE_URL + "api/saveRecipe", jsonRecipe, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
            }
        }, null
        );
        queue.add(request);
    }

}
