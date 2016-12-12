package com.equityinfo.pipealarm.util.imageLoad;

import android.content.Context;
import android.widget.ImageView;

import com.equityinfo.pipealarm.R;
import com.equityinfo.pipealarm.setting.Setting;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by user on 2016/9/22.
 */
public class LoadImageforURL {
    private static String Dir=Setting.PCT;
    private static LoadImageforURL loadImageforURL=new LoadImageforURL();

    public static LoadImageforURL getInstance() {
        if (loadImageforURL==null)
            loadImageforURL=new LoadImageforURL();
        return loadImageforURL;
    }

    /**
     *图片先下载至本地 再加载
     * @param context
     * @param url
     * @param imageView
     */
    public synchronized void loadlocal(Context context, String url, ImageView imageView){
        String[] filePaths=url.split("/");
        File dir=new File(Dir);
        if (!dir.exists())
            dir.mkdirs();
        String filePath=Dir+filePaths[filePaths.length-1];
        File file=new File(filePath);
        if (file.exists()){
            Picasso.with(context).load(file).into(imageView);
        }else {
            Picasso.with(context).load(R.drawable.background).into(imageView);//预加载防止图片错位
            Picasso.with(context).load(url).into(new DownloadTarget(context,filePath,imageView));
        }
    }
    /**
     *图片加载
     * @param context
     * @param url
     * @param imageView
     */
    public synchronized void load(Context context, String url, ImageView imageView){
        Picasso.with(context).load(R.drawable.background).into(imageView);//预加载防止图片错位
        Picasso.with(context).load(url).into(imageView);

    }
}
