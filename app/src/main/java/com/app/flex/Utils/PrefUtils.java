package com.app.flex.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.app.flex.model.User;
import com.google.gson.Gson;



public class PrefUtils {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor=null;

    public static final String PREF_USER_INFO="PREF_USER_INFO";


    public static void initPreferance(Context context){

        if (sharedPreferences==null){
            sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
        }

        if (editor==null){
            editor=sharedPreferences.edit();
        }
    }

    public static boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    public static void putString(String key, String value) {
        editor.putString(key, value).commit();
    }

    public static String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public static void remove(String key) {
        if (contains(key))
            editor.remove(key).commit();
    }

    public static void addUserInfo(User login) {
        putString(PREF_USER_INFO, new Gson().toJson(login));
    }

    public static User getUserInfo() {
        return new Gson().fromJson(getString(PREF_USER_INFO), User.class);
    }

}
