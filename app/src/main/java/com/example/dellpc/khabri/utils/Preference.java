package com.example.dellpc.khabri.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preference {
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private Context mContext = null;
    private String prefName = "khabri";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public Preference(Context mContext)
    {
        this.mContext = mContext;
        int PRIVATE_MODE = 0;
        pref = mContext.getSharedPreferences(prefName, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void setData(Context mContext, String GcId) {
        SharedPreferences mPreference = mContext.getSharedPreferences(prefName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mPreference.edit();
        mEditor.putString("GcId", GcId);
        mEditor.apply();
    }
    public final String getData(Context mContext) {
        SharedPreferences mPreference = mContext.getSharedPreferences(prefName,
                Context.MODE_PRIVATE);
        String arr = mPreference.getString("GcId", "");
        return arr;
    }


    public String getString(String key, String def) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(mContext);
        String s = prefs.getString(key, def);
        return s;
    }

    public void setString(String key, String val) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor e = prefs.edit();
        e.putString(key, val);
        e.apply();
    }

    public void clearData(Context mContext) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor mEditor = prefs.edit();
        mEditor.clear();
        mEditor.apply();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.apply();
    }

    public void setLatitude(String Latitude) {
        setString("Latitude", Latitude);
    }
    public String getLatitude()
    {
        return getString("Latitude", "");
    }

    public void setLongitude(String Longitude) {
        setString("Longitude", Longitude);
    }
    public String getLongitude()
    {
        return getString("Longitude", "");
    }


}
