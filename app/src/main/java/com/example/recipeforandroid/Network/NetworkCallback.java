package com.example.recipeforandroid.Network;

public interface NetworkCallback<T> {

    void onSuccess(T result);

    void onFailure(String errorString);
}
