package com.example.dellpc.khabri.ui.splash;

import com.example.dellpc.khabri.ui.base.MvpPresenter;
import com.example.dellpc.khabri.ui.base.MvpView;

/**
 * @author nimi_0112
 * Created on 16-11-2018
 */
public interface SplashMvpPresenter<V extends SplashMvpView> extends MvpPresenter<V> {

    void iniliatizeAddtionalClients();

    void checkInternet();

    void noInternet();

    void enableLocation();

    void requestPermission();


    void getUserLocation();
}
