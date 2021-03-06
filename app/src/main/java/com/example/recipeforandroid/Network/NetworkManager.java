package com.example.recipeforandroid.Network;

import android.app.Application;
import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.recipeforandroid.Helpers.IbbHelper;
import com.example.recipeforandroid.Persistence.Entities.Recipe;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class NetworkManager extends Application{

    private Context context;
    private static final String BASE_URL = "http://10.0.2.2:8080/";
    JSONObject userLogin = new JSONObject();
    RequestQueue queue;

    public NetworkManager(Context context) {
        this.context = context;
    }

    public void login(String username, String password, final NetworkCallback callBack) {
        queue = Volley.newRequestQueue(context);
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
        queue = Volley.newRequestQueue(context);
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
        queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(BASE_URL +"api/" + id + "/recipeList", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
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

    public void saveRecipe(Recipe recipe, final NetworkCallback callback) {
        queue = Volley.newRequestQueue(context);
        JSONObject jsonRecipe = new JSONObject();

        try {
            jsonRecipe.put("userID", recipe.getUserID());
            jsonRecipe.put("recipeTitle", recipe.getRecipeTitle());
            jsonRecipe.put("recipeText", recipe.getRecipeText());
            jsonRecipe.put("recipeTag", recipe.getRecipeTag());
            jsonRecipe.put("id", recipe.getID());
            jsonRecipe.put("recipeImage", recipe.getRecipeImage());

        } catch (JSONException e) {
            e.printStackTrace();
        }



        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BASE_URL + "api/saveRecipe", jsonRecipe, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
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

    public void deleteRecipe (Recipe recipe, final NetworkCallback callback) {
        queue = Volley.newRequestQueue(context);
        Long id = recipe.getID();
        JsonObjectRequest request = new JsonObjectRequest(BASE_URL +"api/" + id + "/deleteRecipe", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
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

    public void deleteUser (long id, final NetworkCallback callback) {
        queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(BASE_URL + "api/" + id + "/deleteUser", new Response.Listener<JSONObject>() {
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

    public void uploadImage (String byte64Image, final NetworkCallback callback) {
        queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.imgbb.com/1/upload",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Response: " +response);
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("expiration", IbbHelper.getEXPIRATION());
                params.put("key", IbbHelper.getKEY());
                params.put("image", byte64Image);
                return params;
            }

        };

        queue.add(stringRequest);
    }
    public void isLoggedIn(String username, String password, final NetworkCallback callback) {
        queue = Volley.newRequestQueue(context);
        try {
            userLogin.put("username", username);
            userLogin.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BASE_URL + "api/isLoggedIn", userLogin, new Response.Listener<JSONObject>() {
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

    public void changePassword(String username, String password, final NetworkCallback callback) {
        queue = Volley.newRequestQueue(context);
        try {
            userLogin.put("username", username);
            userLogin.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BASE_URL + "api/changePassword", userLogin, new Response.Listener<JSONObject>() {
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

}
