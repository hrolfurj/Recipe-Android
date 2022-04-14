package com.example.recipeforandroid.Network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.recipeforandroid.Persistence.Entities.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class NetworkManager {

    private static final String BASE_URL = "http://10.0.2.2:8080/";
    private static NetworkManager mInstance;
    private static RequestQueue mQueue;
    private Context mContext;

    public static synchronized NetworkManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetworkManager(context);
        }
        return mInstance;
    }

    private NetworkManager(Context context){
        mContext = context;
    }

    public RequestQueue getRequestQueue() {
        if (mQueue == null){
            mQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mQueue;
    }


    public void getRecipes(NetworkCallback<List<Recipe>> callback) {
        StringRequest request = new StringRequest(
                Request.Method.GET, BASE_URL + "api/recipes", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Recipe>>(){}.getType();
                List<Recipe> recipeList = gson.fromJson(response, listType);
                callback.onSuccess(recipeList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }
        );
    }



    /*
    public void postReview(JSONObject requestBody, long selectedHike, NetworkCallback<Hike> callback) {
        mRequestHelper.post(BASEURL + "hikes/" + selectedHike + "/reviews" , requestBody, new NetworkCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Hike hike = gson.fromJson(result, Hike.class);
                callback.onSuccess(hike);
            }

            @Override
            public void onFailure(String error) {
                callback.onFailure(error);
            }
        });
    }
     */

}
