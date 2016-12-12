package com.equityinfo.pipealarm.view.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.equityinfo.pipealarm.R;
import com.equityinfo.pipealarm.setting.Setting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/7/8.
 */
public class AvatarFragment extends Fragment {

    private ImageView icon_avator;  // 头像
    // -------------------------- avator
    private View avatorView;
    private int mIsSupportCameraFeature = -1;
    private PopupWindow avatorPopupWindow;
    // 头像文件
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";
    // 请求识别码
    private static final int LOCAL_REQUEST_CODE = 0xa0;
    private static final int CAMERA_REQUEST_CODE = 0xa1;
    private static final int RESULT_REQUEST_CODE = 0xa2;
    String picture;
    // 剪裁后图片的宽高
    private static int output_x = 300;
    private static int output_y = 300;
    // 头像路径
    File file;
    String fileUrl;
    private String img_avator_path;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    public static  String pct = Setting.PCT;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_avator_set, null);
        LinearLayout layout=(LinearLayout) view.findViewById(R.id.lay_userfragment);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 得到控件
        icon_avator = (ImageView) getView().findViewById(R.id.icon_avator_set);
        // 设置头像
        // 实例化
        preferences = getActivity().getSharedPreferences("User_Info", getActivity().MODE_PRIVATE);
        editor = preferences.edit();
        img_avator_path = preferences.getString("head_img_path", null); // 头像路径
        if (img_avator_path != null) {
            Uri uri = Uri.fromFile(new File(img_avator_path));
            ContentResolver contentResolver = getActivity().getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(contentResolver
                        .openInputStream(uri));
                Bitmap bit = toRoundBitmap(bitmap);
                icon_avator.setImageBitmap(bit);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        icon_avator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 实例化头像设置
                initSetAvatorPop();
            }
        });
    }

    public void initSetAvatorPop(){
        // layout
        avatorView = LayoutInflater.from(getActivity()).inflate(R.layout.pop_img_choose, null);
        avatorPopupWindow = new PopupWindow(avatorView, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        // 点击外部消失
        avatorPopupWindow.setOutsideTouchable(true);
        avatorPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        avatorPopupWindow.setFocusable(true);
        // show location
        avatorPopupWindow.showAtLocation(avatorView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        // widget
        TextView tv_take_photo = (TextView) avatorView.findViewById(R.id.pop_take_photo);
        TextView tv_local_img = (TextView) avatorView.findViewById(R.id.pop_local_img);
        TextView tv_cancel = (TextView) avatorView.findViewById(R.id.pop_cancel);
        // 拍照
        tv_take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFromCamera();
            }
        });
        // 选择本地图片
        tv_local_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFromLocal();
            }
        });
        // 取消
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avatorPopupWindow.dismiss();
            }
        });
    }

    /** avator set **/
    /**
     * 从本地选取图片
     * */
    private void chooseFromLocal() {
        avatorPopupWindow.dismiss();
        // 从本地选择图片(调用手机自身图像浏览工具浏览)
        Intent intentFromLocal = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intentFromLocal.setType("image/*");
        PackageManager manager = getActivity().getPackageManager();
        List<ResolveInfo> apps = manager.queryIntentActivities(intentFromLocal, 0);
        if (apps.size() > 0) {
            startActivityForResult(intentFromLocal, LOCAL_REQUEST_CODE);
        }
    }
    private void chooseFromCamera() {
        if (!isSupportCameraFeature(getActivity())) {
            Toast.makeText(getActivity(), "not support video", Toast.LENGTH_LONG)
                    .show();
        } else {
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(intent, 0);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File path1 = new File(pct);
            if (!path1.exists()) {
                path1.mkdirs();
            }
            File file = new File(path1, IMAGE_FILE_NAME );
            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CAMERA},
                        1);
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
                picture = file.getName();
            }
        }
    }
        //判断是否支持相机功能

    private boolean isSupportCameraFeature(Context context) {
        if (mIsSupportCameraFeature != (-1)) {
            return mIsSupportCameraFeature == 1;
        } else {
            PackageManager pm = context.getPackageManager();
            FeatureInfo[] features = pm.getSystemAvailableFeatures();
            for (FeatureInfo f : features) {
                Log.v("shenwenjian", "f" + f.name);
                if (f.name.equals(PackageManager.FEATURE_CAMERA)) {
                    mIsSupportCameraFeature = 1;
                    return true;
                }
            }
        }
        return false;
    }
//    /**
//     * 拍照
//     * */
//    private void chooseFromCamera() {
//        avatorPopupWindow.dismiss();
//        Intent intentFromCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // 判断SD卡是否可用
//        if (hasSDCard()) {
//            intentFromCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri
//                    .fromFile(new File(Environment
//                            .getExternalStorageDirectory(), IMAGE_FILE_NAME)));
//        }
//        startActivityForResult(intentFromCamera, CAMERA_REQUEST_CODE);
//    }
     //EquityAuditXuhui
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_CANCELED) {  // 用户没有进行有效操作，返回
            Toast.makeText(getActivity(), "取消", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (requestCode) {
            case LOCAL_REQUEST_CODE:
                cropRawPhoto(data.getData());
                break;

            case CAMERA_REQUEST_CODE:
//                if (hasSDCard()) {
//                    File tempFile = new File(
//                            Environment.getExternalStorageDirectory() + "/"
//                                    + IMAGE_FILE_NAME);
//                    cropRawPhoto(Uri.fromFile(tempFile));
//                } else {
//                    Toast.makeText(getActivity(), "没有SD卡", Toast.LENGTH_SHORT).show();
//                }
                if (data == null){
                    File tempFile = new File(pct,IMAGE_FILE_NAME);
                    cropRawPhoto(Uri.fromFile(tempFile));

                    return;
                }


                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");

//            fileUrl = savePhoto(bitmap);
                    File f = new File(pct);
                    if (!f.exists()){
                        f.mkdirs();
                    }
                    file = new File(pct,IMAGE_FILE_NAME+".jpg");
                    fileUrl = file.getAbsolutePath();
                    try {
                        FileOutputStream fos = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);

                        fileUrl = moveFile(fileUrl,pct);
                        fos.flush();
                        fos.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                break;

            case RESULT_REQUEST_CODE:
                if (data != null) {
                    // 保存
                    String img_path = toFile(data);
                    editor.putString("head_img_path", img_path);
                    editor.commit();
                    setImageToHead(data);
                    // 删除旧图
                    if (img_avator_path != null) {
                        new File(img_avator_path).delete();
                        // 发送广播刷新（缩略图）
                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        Uri uri = Uri.fromFile(new File(img_avator_path));
                        intent.setData(uri);
                        getActivity().sendBroadcast(intent);
                    }
                }
                break;
        }
    }

    /**
     * 由Uri获取图片SD卡路径 (Uri转File)
     * */
    public String toFile(Intent data) {
        Uri uri = data.getData();
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor actualimagecursor = getActivity().managedQuery(uri, proj, null, null, null);
        int actual_image_column_index = actualimagecursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        // img_path 图片路径
        String img_path = actualimagecursor
                .getString(actual_image_column_index);
        // File file = new File(img_path); 转为File
        return img_path;
    }
    public String moveFile(String srcFileName, String destDirName) {

        File srcFile = new File(srcFileName);
        if(!srcFile.exists() || !srcFile.isFile())
            return "";

        File destDir = new File(destDirName);
        if (!destDir.exists())
            destDir.mkdirs();
        File f = new File(destDirName + srcFile.getName());
        srcFile.renameTo(f);
        return f.getAbsolutePath();
    }
    /**
     * 剪裁图片
     * */
    public void cropRawPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        // 设置intent的data和Type属性
        intent.setDataAndType(uri, "image/*");
        // 设置剪裁(crop=true是设置在开启的Intent中设置显示的VIEW可裁剪)
        intent.putExtra("crop", true);
        // 宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 剪裁图片宽高
        intent.putExtra("outputX", output_x);
        intent.putExtra("outputY", output_y);
        intent.putExtra("return_data", true);
        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    /**
     * 提取保存剪裁后的图片数据，并设置头像
     * */
    private void setImageToHead(Intent intent) {
        Uri uri = intent.getData();
        ContentResolver contentResolver = getActivity().getContentResolver();
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(contentResolver
                    .openInputStream(uri));
            Bitmap bit = toRoundBitmap(bitmap);
            icon_avator.setImageBitmap(bit);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 绘制圆形头像
     * @param bitmap
     * @return
     */
    public Bitmap toRoundBitmap(Bitmap bitmap) {
        //圆形图片宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        //正方形的边长
        int r = 0;
        //取最短边做边长
        if(width > height) {
            r = height;
        } else {
            r = width;
        }
        //构建一个bitmap
        Bitmap backgroundBmp = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        //new一个Canvas，在backgroundBmp上画图
        Canvas canvas = new Canvas(backgroundBmp);
        Paint paint = new Paint();
        //设置边缘光滑，去掉锯齿
        paint.setAntiAlias(true);
        //宽高相等，即正方形
        RectF rect = new RectF(0, 0, r, r);
        //通过制定的rect画一个圆角矩形，当圆角X轴方向的半径等于Y轴方向的半径时，
        //且都等于r/2时，画出来的圆角矩形就是圆形
        canvas.drawRoundRect(rect, r/2, r/2, paint);
        //设置当两个图形相交时的模式，SRC_IN为取SRC图形相交的部分，多余的将被去掉
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //canvas将bitmap画在backgroundBmp上
        canvas.drawBitmap(bitmap, null, rect, paint);
        //返回已经绘画好的backgroundBmp
        return backgroundBmp;
    }

    /**
     * 检查是否存在SD卡
     * */
    public boolean hasSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }


}
