package com.equityinfo.pipealarm.model.interfaces;

import com.equityinfo.pipealarm.util.http.AsyncCallBack;

/**
 * Created by user on 2016/12/8.
 */
public interface MainInter {
    void getcode(AsyncCallBack callBack);
    void getwarning(String code,String tm,String status,String f,String r,AsyncCallBack callBack);
    void updateStatus(String id,AsyncCallBack callBack);

}
