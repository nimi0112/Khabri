package com.example.dellpc.khabri.data;

/**
 * @author nimi_0112
 * Created on 16-11-2018
 */

public class DataManager {

    SharedPrefsHelper mSharedPrefsHelper;

    public DataManager(SharedPrefsHelper sharedPrefsHelper) {
        mSharedPrefsHelper = sharedPrefsHelper;
    }

    public void clear() {
        mSharedPrefsHelper.clear();
    }

    public void setLatitude(String Latitude) {
        mSharedPrefsHelper.setLatitude(Latitude);
    }
    public String getLatitude() {
        return mSharedPrefsHelper.getLatitude();
    }

    public void setLongitude(String Longitude) {
        mSharedPrefsHelper.setLongitude(Longitude);
    }

    public String getLongitude() {
        return mSharedPrefsHelper.getLongitude();
    }
}
