package com.equityinfo.pipealarm.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.equityinfo.pipealarm.setting.Setting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author lxs
 */
public class Utils {
    public static final String FILE_CACHE = Setting.CACHE;
    private static final String FORMAT_DATE_TIME = "yyyy/MM/dd HH:mm:ss";
    private static final String STR = "Sx3_Et4f7n800UHjJkKl_J9hg3RyO_5oJbRevMPoK4hG5fDe_2WsZqhMl3PmT4fC_5dE";

    /**
     * 生成随机文件名
     *
     * @param file
     * @return
     */
    public static String getRandomFileName(File file) {
        String suffix = file.getName().split("[.]")[1];
        return getTimeStamp() + getRandomString(10) + "." + suffix;
    }

    /**
     * 获取当前时间戳
     *
     * @return
     */
    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis());

    }

    /**
     * 获取指定长度的随机字符串
     *
     * @return
     */
    public static String getRandomString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(STR.charAt(random.nextInt(STR.length())));
        }
        return sb.toString();
    }

    /**
     * 获取当前时间 时间格式为:yyyy/MM/dd
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getThisTime() {
        Date date = new Date(System.currentTimeMillis());
        return new SimpleDateFormat(FORMAT_DATE_TIME).format(date);
    }

    /**
     * 获取数天的日期时间格式为:yyyy/MM/dd
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static List<String> getTimes(int num) {
        Long dataTimeMillis = (long) 24 * 60 * 60 * 1000;
        Long today = System.currentTimeMillis();
        List<String> times = new ArrayList<>();
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < num; i++) {
            times.add(time.format(new Date(today)));
            today -= dataTimeMillis;
        }
        return times;
    }

    /**
     * 删除目录下的所有文件
     *
     * @param file
     */
    public static void DeleteFile(File file) {
        if (file.isFile()) {
            file.delete();
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }

            for (File f : childFile) {
                DeleteFile(f);
            }

            file.delete();
        }

    }

    /**
     * 获取路径下的文件
     *
     * @param path
     * @return
     */
    public static LinkedList<File> getFile(String path) {
        LinkedList<File> files;
        File file = new File(path);
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile != null) {
                files = new LinkedList<File>();
                for (File file2 : childFile) {
                    files.add(file2);
                }
                return files;
            }
        }

        return null;

    }

    /**
     * 保存到文件
     *
     * @param s
     * @param fileName
     */
    public static void saveToFile(String s, String fileName) {
//		String path = Environment.getExternalStorageDirectory().getAbsolutePath();

//		File dir = new File(path+FILE_CACHE);
        File dir = new File(FILE_CACHE);
        Log.i("path1111", FILE_CACHE);
        if (!dir.exists()) {
            dir.mkdirs();
            Log.i("创建", FILE_CACHE);
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(dir + "/" + fileName + ".txt");
            fileOutputStream.write(s.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 文件转化为字符串
     *
     * @param fileName
     * @return
     */
    public static String parseFileToString(String fileName) {
//		String path = Environment.getExternalStorageDirectory().getAbsolutePath()+FILE_CACHE+identity+"/"+fileName+".txt";
        String path = FILE_CACHE + "/" + fileName + ".txt";
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuffer buffer = new StringBuffer();
            String str = "";
            while ((str = reader.readLine()) != null) {
                buffer.append(str);
            }
            reader.close();
            return buffer.toString();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String timeStamp2Date(String seconds) {
        int len = seconds.length();
        String data = seconds;
        if (len == 10)
            data = seconds + "000";
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(data)));
    }

    /**
     * 对图片进行压缩
     */
    public static File scal(String path) {
//		String path = fileUri.getPath();
        File outputFile = new File(path);
        String fileName = outputFile.getName();
        long fileSize = outputFile.length();
        final long fileMaxSize = 200 * 1024;
        if (fileSize >= fileMaxSize) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            int height = options.outHeight;
            int width = options.outWidth;

            double scale = Math.sqrt((float) fileSize / fileMaxSize);
            options.outHeight = (int) (height / scale);
            options.outWidth = (int) (width / scale);
            options.inSampleSize = (int) (scale + 0.5);
            options.inJustDecodeBounds = false;

            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            outputFile = new File(createImageFile(fileName).getPath());
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(outputFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
                fos.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
//			Log.d(, sss ok  + outputFile.length());
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            } else {
                File tempFile = outputFile;
                outputFile = new File(createImageFile(fileName).getPath());
                copyFileUsingFileChannels(tempFile, outputFile);
            }

        }
        return outputFile;

    }

    public static Uri createImageFile(String FileName) {
        // Create an image file name
//		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//		String imageFileName = timeStamp;
        File fileDir = new File(Setting.CACHE);
        if (!fileDir.exists())
            fileDir.mkdirs();
        File image = null;
        try {
            image = new File(Setting.CACHE + FileName);
            if (!image.exists())
                image.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Save a file: path for use with ACTION_VIEW intents
        return Uri.fromFile(image);
    }

    public static void copyFileUsingFileChannels(File source, File dest) {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            try {
                inputChannel = new FileInputStream(source).getChannel();
                outputChannel = new FileOutputStream(dest).getChannel();
                outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } finally {
            try {
                inputChannel.close();
                outputChannel.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static String getIdForString(String result) {
        result = result.trim();
        String str2 = "";
        if (result != null && !"".equals(result)) {
            for (int i = 0; i < result.length(); i++) {
                if (result.charAt(i) >= 48 && result.charAt(i) <= 57) {
                    str2 += result.charAt(i);
                    if (i == result.length() - 1) {
                        if (result.charAt(i) == 'x' || result.charAt(i) == 'X')
                            str2 += result.charAt(i);
                    }
                }
            }
        }
        return str2;
    }

    public static void showToast(Context context, String toast, int timeLong) {
        Toast.makeText(context, toast, timeLong).show();
    }

    public static void showToast(Context context, String toast) {
        showToast(context, toast, Toast.LENGTH_SHORT);
    }
}