package com.equityinfo.pipealarm.util.http;

import android.content.Context;

import java.util.Map;

public class HttpReq {
	public static final String HTTP_POST = "POST";
	public static final String HTTP_GET = "GET";
	private AsyncRequest asyncRequest;
	private boolean isApplication_json = true;
	private boolean isFormCommit = false;
	private boolean isMultipart = false;
//	private String requestMethod = HTTP_POST;
    private String requestMethod = HTTP_GET;

	public  void doReq(Context context, String url, AsyncCallBack callBack){
		doReq(context, url, null, callBack);
	}

	public  void doReq(Context context, String url,AsyncParam param, AsyncCallBack callBack){
		doReq(context, url, null, param,callBack);
	}

//	public void doReq(Context context, String url,Map<String, String> header, AsyncCallBack callBack){
//		doReq(context, url, header, null, callBack);
//	}

	public  void doReq(Context context, String url,Map<String, String> header,AsyncParam param, AsyncCallBack callBack){
		if(isApplication_json){
			JsonCommitEntity jsonCommitEntity = new JsonCommitEntity(param,header);
			jsonCommitEntity.setRequestMethod(requestMethod);
			asyncRequest = new AsyncRequest(jsonCommitEntity);
		}else if(isFormCommit){
			FormCommitEntity formCommitEntity = new FormCommitEntity(param, header);
			formCommitEntity.setRequestMethod(requestMethod);
			asyncRequest = new AsyncRequest(formCommitEntity);
		}else if(isMultipart){
			asyncRequest = new AsyncRequest(new MultipartEntity(param, header));
		}
//		asyncRequest.post(Setting.DOMAIN_URL+url, callBack);
		asyncRequest.post(context, url, callBack);
	}
	
	/**
	 * 是否使用json提交方式
	 * @param
	 * @return
	 */
	public HttpReq setApplication_json(){
		this.isApplication_json = true;
		this.isFormCommit = false;
		this.isMultipart = false;
		return this;
	}
	
	/**
	 * 是否使用表单提交方式
	 * @param
	 * @return
	 */
	public HttpReq setFormCommit(){
		this.isFormCommit = true;
		this.isApplication_json = false;
		this.isMultipart = false;
		return this;
	}
	
	/**
	 * 是否使用混合提交方式
	 * @param
	 * @return
	 */
	public HttpReq setMultipart(){
		this.isMultipart = true;
		this.isApplication_json = false;
		this.isFormCommit = false;
		return this;
	}
	
	/**
	 * 设置请求类型为get或post
	 * @param requestMethod
	 * @return
	 */
	public HttpReq setRequestMethod(String requestMethod){
		this.requestMethod = requestMethod;
		return this;
	}
	
}
