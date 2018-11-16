package com.example.dellpc.khabri.ui.splash;

import com.example.dellpc.khabri.ui.base.MvpView;

/**
 * @author nimi_0112
 * Created on 16-11-2018
 */
public interface SplashMvpView extends MvpView {


    void initializeClients();



    boolean isOnline();

    boolean CheckLocationPermission();

    void startLocationPermissionRequest();

    void dialognointernet();

    void getLastLocation();

    void showSettingDialog();
}
