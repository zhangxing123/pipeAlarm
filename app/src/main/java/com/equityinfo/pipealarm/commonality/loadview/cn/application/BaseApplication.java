package com.equityinfo.pipealarm.commonality.loadview.cn.application;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
/**
 * @author wang
 * @version ����ʱ�䣺2015��5��20�� ����10:06:42
 * ��˵�� ��ʼ��������
 */
public class BaseApplication extends Application {
	//��ȡ�����̵߳�������
    private static BaseApplication mContext = null;
    //��ȡ�����̵߳�handler
    private static Handler mMainThreadHandler = null;
    //��ȡ�����̵߳�looper
    private static Looper mMainThreadLooper = null;
    //��ȡ�����߳�
    private static Thread mMainThead = null;
    //��ȡ�����̵߳�id
    private static int mMainTheadId ;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		this.mContext = this;
	    this.mMainThreadHandler = new Handler();
	    this.mMainThreadLooper = getMainLooper();
	    this.mMainThead = Thread.currentThread();
	    //android.os.Process.myUid()��ȡ���û�id
	    //android.os.Process.myPid();//��ȡ������id
	    //android.os.Process.myTid()��ȡ�������̵߳�id
	    this.mMainTheadId = android.os.Process.myTid();//������id
	}
	public static BaseApplication getApplication(){
		return mContext;
	}
	public static Handler getMainThreadHandler(){
		return mMainThreadHandler;
	}
	public static Looper getMainThreadLooper(){
		return mMainThreadLooper;
	}
	public static Thread getMainThread(){
		return mMainThead;
	}
	public static int getMainThreadId(){
		return mMainTheadId;
	}
}
