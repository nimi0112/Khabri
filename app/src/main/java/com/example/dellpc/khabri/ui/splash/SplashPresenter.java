package com.example.dellpc.khabri.ui.splash;

import com.example.dellpc.khabri.data.DataManager;
import com.example.dellpc.khabri.ui.base.BasePresenter;
import com.example.dellpc.khabri.ui.base.MvpView;

/**
 * @author nimi_0112
 * Created on 16-11-2018
 */
public class SplashPresenter<V extends SplashMvpView> extends BasePresenter<V> implements SplashMvpPresenter<V> {

    public SplashPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void iniliatizeAddtionalClients() {
    }

    @Override
    public void checkInternet() {
            getMvpView().initializeClients();

            if (getMvpView().isOnline()){
                if (!getMvpView().CheckLocationPermission()){
                    getMvpView().startLocationPermissionRequest();
                } else {
                    getMvpView().getLastLocation();
                }
                //
            } else {
                getMvpView().dialognointernet();
            }
        }

    @Override
    public void noInternet() {
        getMvpView().dialognointernet();
    }

    @Override
    public void getUserLocation() {
        getMvpView().getLastLocation();
    }

    @Override
    public void requestPermission() {
        getMvpView().startLocationPermissionRequest();
    }

    @Override
    public void enableLocation() {
        getMvpView().showSettingDialog();
    }
}
