package com.androidproject.popularmovies11;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Utility {

    public static String getSortPreference (Context context){

        SharedPreferences sharedPreferences = PreferenceManager.
                getDefaultSharedPreferences(context);
        return sharedPreferences.getString(
                context.getString(R.string.key_pref),
                context.getString(R.string.default_preference));
    }
}
