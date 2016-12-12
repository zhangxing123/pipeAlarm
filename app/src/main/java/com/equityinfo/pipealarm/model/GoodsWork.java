package com.equityinfo.pipealarm.model;

import android.content.Context;

import com.equityinfo.pipealarm.setting.Setting;
import com.equityinfo.pipealarm.util.http.AsyncCallBack;
import com.equityinfo.pipealarm.util.http.AsyncParam;
import com.equityinfo.pipealarm.util.http.HttpReq;

/**
 * Created by john on 16/6/14.
 */
public class GoodsWork {
    private AsyncParam param;
    private HttpReq req;
    private Context context;
    private static String DOM= Setting.DOMAIN;
    public GoodsWork(Context context){
        param = new AsyncParam();
        req = new HttpReq();
        this.context=context;
    }

    public void getCode(AsyncCallBack callBack){
        getRequest("getcode.php",callBack);
    }
    public void getWarning(String code,String tm,String status,String f,String r, AsyncCallBack callBack){
        getRequest("getwarning.php?code="+code+"&tm_="+tm+"&status="+status+"&f="+f+"&r="+r,callBack);
    }
    public void updateStatus(String id,AsyncCallBack callBack){
        getRequest("update_status.php?id="+id,callBack);
    }
    private void request(String action,String jsonParam,AsyncCallBack callBack){
//        Map<String,String> m = new HashMap<>();
//        if(activity!=null){
//        SharedPreferences sharedPreferences=activity.getSharedPreferences(Setting.LOGINSHARE, Context.MODE_PRIVATE);
//        String cookie=sharedPreferences.getString("cookie","");
//        m.put("Cookie",cookie);}
        if(jsonParam == null) {
            param = null;
        }else{
            param.put(jsonParam);
        }
        req.setApplication_json()
                .setRequestMethod(HttpReq.HTTP_POST)
                .doReq(context,action,param,callBack);
    }
    private void getRequest(String action,AsyncCallBack callBack){
//        Map<String,String> m = new HashMap<>();
//        if(c!=null){
//            SharedPreferences sharedPreferences=activity.getSharedPreferences(Setting.LOGINSHARE, Context.MODE_PRIVATE);
//            String cookie=sharedPreferences.getString("cookie","");
//            m.put("Cookie",cookie);}
        new HttpReq().setApplication_json()
                .setRequestMethod(HttpReq.HTTP_GET)
                .doReq(context,DOM+action,callBack);
    }
}
