package com.equityinfo.pipealarm.model;

import android.content.Context;

import com.equityinfo.pipealarm.model.interfaces.MainInter;
import com.equityinfo.pipealarm.util.http.AsyncCallBack;

/**
 * Created by user on 2016/12/8.
 */
public class MainModel implements MainInter{
    private Context context;
   public MainModel(Context context){
        this.context=context;
    }
    @Override
    public void getcode(AsyncCallBack callBack) {
           new GoodsWork(context).getCode(callBack);
    }

    @Override
    public void updateStatus(String id, AsyncCallBack callBack) {
            new GoodsWork(context).updateStatus(id,callBack);
    }

    @Override
    public void getwarning(String code, String tm, String status, String f, String r, AsyncCallBack callBack) {
        new GoodsWork(context).getWarning(code,tm,status,f,r,callBack);
    }
}

