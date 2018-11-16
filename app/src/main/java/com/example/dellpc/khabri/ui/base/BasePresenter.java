package com.example.dellpc.khabri.ui.base;

import com.example.dellpc.khabri.data.DataManager;

/**
 * @author nimi_0112
 * Created on 16-11-2018
 */
public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private DataManager mDataManager;
    private V mMvpView;


    public BasePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    protected V getMvpView() {
        return mMvpView;
    }

    protected DataManager getDataManager() {
        return mDataManager;
    }
}
