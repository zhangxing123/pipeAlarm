package com.equityinfo.pipealarm.util.http;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author lxs
 *
 */
public class AsyncReq {
    private static final String TAG = "AsyncRequest";
    public static final String CHARSET = "UTF-8";
    public static final int TIMEOUT = 10*1000;

    private static int maxPoolSize = 10;
    private static ExecutorService pool = Executors.newFixedThreadPool(maxPoolSize);
    private static Handler handler = new Handler(Looper.getMainLooper());

    private final static int UPDATE_LOADING = 3;
    private final static int UPDATE_FAILURE = 2;
    private final static int UPDATE_SUCCESS = 1;

    private HttpURLConnection connection;
    private URL url;
    private AsyncInter asyncInter;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public AsyncReq(){}

    public AsyncReq(AsyncInter asyncInter){
        this.asyncInter = asyncInter;
    }

    public void post(String urlPath , AsyncCallBack callBack){
        pool.execute(new mRunnable(urlPath, callBack));
    }

    class mRunnable implements Runnable{
        String urlPath;
        AsyncCallBack callBack;

        public mRunnable(String urlPath,AsyncCallBack callBack) {
            // TODO Auto-generated constructor stub
            this.callBack = callBack;
            this.urlPath = urlPath;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            setAsyncRequest(urlPath, callBack);
        }

    }

    class MyRunnable implements Runnable{
        int type;
        AsyncCallBack callBack;
        String result;
        int progress;

        public MyRunnable(int type,String result , AsyncCallBack callBack) {
            // TODO Auto-generated constructor stub
            this.callBack = callBack;
            this.result = result;
            this.type = type;
        }

        public MyRunnable(int type,int progress , AsyncCallBack callBack) {
            // TODO Auto-generated constructor stub
            this.callBack = callBack;
            this.progress = progress;
            this.type = type;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            switch (type) {
                case UPDATE_SUCCESS:
                    callBack.onSuccess(result);
                    break;
                case UPDATE_FAILURE:
                    callBack.onFailure(result);
                    break;
                case UPDATE_LOADING:

                    break;

                default:
                    break;
            }
        }
    }

    public void setAsyncRequest(String urlPath , final AsyncCallBack callBack){
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            try {
                Log.d(TAG,urlPath);
                url = new URL(urlPath);
                connection = (HttpURLConnection) url.openConnection();
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                //url打开失败
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            connection.setConnectTimeout(TIMEOUT);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            try {
                connection.setRequestMethod("GET");
            } catch (ProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

            if(asyncInter.getHeader()!=null){
                for(String key:asyncInter.getHeader().keySet()){
                    connection.setRequestProperty(key, asyncInter.getHeader().get(key));
                }
            }
            if(asyncInter.isHaveParam()){
                connection.setDoOutput(true);
                try {
                    outputStream = new BufferedOutputStream(connection.getOutputStream());
                    asyncInter.writeTo(outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else{
                connection.setDoOutput(false);
            }

            try {
                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    inputStream = new BufferedInputStream(connection.getInputStream());
                    String result =  parseByte2String(inputStream);
                    System.out.println(connection.getContentType());
//                    Log.d(TAG, "chenggong"+result);
                    handler.post(new MyRunnable(UPDATE_SUCCESS, result, callBack));
                }else{
                    String result = parseByte2String(connection.getErrorStream());
                    Log.d(TAG, "shibai"+result);
                    handler.post(new MyRunnable(UPDATE_FAILURE, result, callBack));
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }finally{
            try {
                if(inputStream!=null){
                    inputStream.close();
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                if(outputStream!=null){
                    outputStream.close();
                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            connection.disconnect();
        }

    }

    public String getUrlWithQueryString(String urlPath, String params) {
        if(params != null) {
            urlPath += "?" + params;
        }
        return urlPath;
    }

    /**
     * 将字节流转化成字符串
     * @param data
     * @return
     * @throws IOException
     */
    public String parseByte2String(InputStream data) throws IOException{
        BufferedReader bReader = new BufferedReader(new InputStreamReader(data));
        StringBuffer sb = new StringBuffer();
        String res = "";
        while ((res = bReader.readLine())!=null) {
            sb.append(res);
        }
        return sb.toString();
    }

}
