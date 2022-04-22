package com.example.recipeforandroid.Services;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {

    private static final String USERNAME_REGEX = "^(?=[a-zA-Z0-9._]{4,20}$)(?!.*[_.]{2})[^_.].*[^_.]$";
    private static final String PASSWORD_REGEX = "^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$";

    public static boolean isLegalUsername(String username) {
        Pattern p = Pattern.compile(USERNAME_REGEX);
        Matcher m = p.matcher(username);
        return m.matches();
    }

    public static boolean isLegalPassword(String password) {
        Pattern p = Pattern.compile(PASSWORD_REGEX);
        Matcher m = p.matcher(password);
        return m.matches();
    }
}
