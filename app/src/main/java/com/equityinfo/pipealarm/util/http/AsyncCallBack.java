package com.equityinfo.pipealarm.util.http;

public interface AsyncCallBack {

	/**
	 * 请求成功
	 * @param
	 */
	public void onSuccess(String result);
	
	/**
	 * 请求失败
	 * @param erroMsg
	 */
	public void onFailure(String erroMsg);
	
}
