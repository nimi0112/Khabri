package com.example.dellpc.khabri.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static android.content.Context.MODE_PRIVATE;

public class SharedPrefsHelper {

    public static final String MY_PREFS = "khabri";

    SharedPreferences mSharedPreferences;

    public SharedPrefsHelper(Context context) {
        mSharedPreferences = context.getSharedPreferences(MY_PREFS, MODE_PRIVATE);
    }

    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }


    public void setLatitude(String Latitude) {
        mSharedPreferences.edit().putString("Latitude", Latitude).apply();
    }
    public String getLatitude() {
        return mSharedPreferences.getString("Latitude", "");
    }

    public void setLongitude(String Longitude) {
        mSharedPreferences.edit().putString("Longitude", Longitude).apply();
    }

    public String getLongitude() {
        return mSharedPreferences.getString("Longitude", "");
    }


}
