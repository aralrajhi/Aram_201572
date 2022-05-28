package com.aram_201572.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public final class SharedPrefsHelper {

    private static final String KEY_FILE_NAME = "student_app_prefs";

    private static final String KEY_CITY= "key_city";


    public static String getCity(final Context ctx) {
        return ctx.getSharedPreferences(
                SharedPrefsHelper.KEY_FILE_NAME, Context.MODE_PRIVATE)
                .getString(KEY_CITY,"Berlin");
    }

    public static void setCity(final Context ctx, String city) {
        final SharedPreferences prefs = ctx.getSharedPreferences(
                SharedPrefsHelper.KEY_FILE_NAME, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_CITY,city);
        editor.commit();
    }



    public static void clearSharedPrefs(final Context ctx){
        SharedPreferences settings = ctx.getSharedPreferences(SharedPrefsHelper.KEY_FILE_NAME, Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }


}

