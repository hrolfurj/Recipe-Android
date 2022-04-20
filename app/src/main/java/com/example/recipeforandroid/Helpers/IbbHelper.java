package com.example.recipeforandroid.Helpers;

public class IbbHelper {

    // expiration in seconds 60-15552000
    private static final String EXPIRATION = "86400";
    private static final String KEY = "0f82514109c0c76e08b51dd84755d946";

    public static String getEXPIRATION() {
        return EXPIRATION;
    }

    public static String getKEY() {
        return KEY;
    }
}