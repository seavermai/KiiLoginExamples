package com.example.seaver.kiiloginexamples;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * This class is a helper class for SharedPreferences operation
 */
public class Pref {
    /**
     * Name of SharedPreferences
     */
    private static final String PREF_NAME = "settings";

    /**
     * Keys of SharedPreferences entry
     */
    interface Key {
        static final String APP_ID = "appId";
        static final String APP_KEY = "appKey";
        static final String SITE = "site";
        static final String STORED_ACCESS_TOKEN = "token";
    }

    /**
     * Save access token
     * @param context
     * @param token
     */
    public static void setStoredAccessToken(Context context, String token) {
        SharedPreferences pref = getSharedPreferences(context);
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(Key.STORED_ACCESS_TOKEN, token);
        edit.commit();
    }

    /**
     * Get access token
     * @param context
     * @return null if token is not stored in SharedPreferences
     */
    public static String getStoredAccessToken(Context context) {
        SharedPreferences pref = getSharedPreferences(context);
        return pref.getString(Key.STORED_ACCESS_TOKEN, null);
    }

    /**
     * @param context
     * @return instance of SharedPreferences
     */
    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
}
