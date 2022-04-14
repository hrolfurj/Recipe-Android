package com.example.recipeforandroid.Network;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.recipeforandroid.Persistence.Entities.Recipe;
import com.example.recipeforandroid.Persistence.Entities.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import javax.xml.transform.ErrorListener;

public class NetworkManager extends Application {

    private static final String BASE_URL = "http://10.0.2.2:8080/";
    private static NetworkManager mInstance;
    private static RequestQueue mQueue;
    private Context mContext;
    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized NetworkManager getInstance() {
        return mInstance;
    }

    public static final String TAG = NetworkManager.class
            .getSimpleName();

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    JSONObject userLogin = new JSONObject();
    //RequestQueue requestQueue = Volley.newRequestQueue(mContext);

    public static synchronized NetworkManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetworkManager(context);
        }
        return mInstance;
    }

    private NetworkManager(Context context){
        mContext = context;
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

<<<<<<< HEAD
    public void login(String username, String password) {
        System.out.println("hh");



        try {
            System.out.println(username);
            userLogin.put("username", username);
            userLogin.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BASE_URL +"/api/login", userLogin, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    System.out.println("jj");
                    System.out.println(response.get(username));
                } catch (JSONException e) {
                    System.out.println("kk");
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        //requestQueue.add(request);
        //System.out.println(request.toString());
        //request.setRequestQueue(mQueue);
        NetworkManager.getInstance().addToRequestQueue(request);
    }

    public void recipeList(int id) {
        System.out.println("in recipeList");

        System.out.println("id:"+id);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "https://jsonplaceholder.typicode.com/todos/1", userLogin, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG, response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        //requestQueue.add(request);
        //System.out.println(request.toString());
        //request.setRequestQueue(mQueue);
        NetworkManager.getInstance().addToRequestQueue(request);
    }

    public void testCon(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://www.google.com";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        VolleyLog.d("Response is: " + response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
=======


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
>>>>>>> 5f05dce1d6ed50f0b9887923a5a1d5864aad3afa

}
