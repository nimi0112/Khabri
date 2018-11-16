package com.example.dellpc.khabri.utils;

import android.app.Application;

import com.example.dellpc.khabri.data.DataManager;
import com.example.dellpc.khabri.data.SharedPrefsHelper;

/**
 * @author nimi_0112
 * Created on 16-11-2018
 */
public class MvpApp extends Application {

    DataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPrefsHelper sharedPrefsHelper = new SharedPrefsHelper(getApplicationContext());
        dataManager = new DataManager(sharedPrefsHelper);

    }

    public DataManager getDataManager() {
        return dataManager;
    }
}
