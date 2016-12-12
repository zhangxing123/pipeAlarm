package com.equityinfo.pipealarm.util.http;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AsyncParam {
	private static final String TAG = "AsyncParam";
	protected ConcurrentHashMap<String, String> urlParam;
	protected ConcurrentHashMap<String, File> fileParam;
	protected String jsonParam = null;
	
	public AsyncParam() {
		// TODO Auto-generated constructor stub
		init();
	}
	
	public void put(String json){
		if(json!=null){
			jsonParam = json;
		}
	}
	
	public void put(String key, String value){
        if(key != null && value != null) {
        	urlParam.put(key, value);
        }
    }

    public void put(String key, File file) throws FileNotFoundException {
    	if(key != null && file != null) {
        	fileParam.put(key, file);
        }
    }
    
    private void init(){
        urlParam = new ConcurrentHashMap<String, String>();
        fileParam = new ConcurrentHashMap<String, File>();
    }
    
    public String urlParamToString(){
    	Log.d(TAG, parseMapToString(urlParam));
		return parseMapToString(urlParam);
    	
    }
    
    public String parseMapToString(Map<String , String> maps){
		if(maps == null){
			return null;
		}
		boolean isFirst = true;
		StringBuffer stringBuffer = new StringBuffer();
		for(String key : maps.keySet()){
			if(isFirst){
				isFirst = false;
			}else{
				stringBuffer.append("&");
			}
			stringBuffer.append(key+"="+maps.get(key));
		}
		return stringBuffer.toString();
		
	}

}
