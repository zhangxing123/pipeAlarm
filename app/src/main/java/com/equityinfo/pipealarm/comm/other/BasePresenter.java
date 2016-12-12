package com.equityinfo.pipealarm.comm.other;


/**
 * Created by zx
 */
public class BasePresenter<V> {
    public V mvpView;
    public void attachView(V mvpView) {
        this.mvpView = mvpView;
    }


    public void detachView() {
        this.mvpView = null;
    }
}
