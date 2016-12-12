package com.equityinfo.pipealarm.util.imageLoad;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by user on 2016/9/22.
 */
public class DownloadTarget implements Target {
    private ImageView imageView;
    private String filePath;
    private Context context;

    public DownloadTarget(Context context, String filePath, ImageView imageView){
        this.context=context;
        this.imageView=imageView;
        this.filePath=filePath;
    }
    @Override
    public void onPrepareLoad(Drawable drawable) {

    }

    @Override
    public void onBitmapFailed(Drawable drawable) {

    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {

        try {
            File file=new File(filePath);
            file.createNewFile();
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            Picasso.with(context).load(file).into(imageView);
        }catch (IOException e){
            e.printStackTrace();
        }



    }
}
