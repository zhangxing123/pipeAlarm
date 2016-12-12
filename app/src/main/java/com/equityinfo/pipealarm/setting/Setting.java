package com.equityinfo.pipealarm.setting;

import android.os.Environment;

/**
 * Created by user on 2016/12/8.
 */
public class Setting {
    public static String PCT= Environment.getExternalStorageDirectory().getAbsolutePath()+"/pipeAlarm/";
    public static String CACHE= PCT+"/cache/";
    public static String DOMAIN="http://10.100.0.1/patrol/portal/service/";
    public static String TAG="pipeDebug";
}
