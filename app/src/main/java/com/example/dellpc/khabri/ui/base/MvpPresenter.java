package com.example.dellpc.khabri.ui.base;

/**
 * @author nimi_0112
 * Created on 16-11-2018
 */
public interface MvpPresenter<V extends MvpView> {

     void onAttach(V mvpView);

}
