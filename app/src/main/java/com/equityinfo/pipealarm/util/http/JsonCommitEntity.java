package com.equityinfo.pipealarm.util.http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class JsonCommitEntity implements AsyncInter{
	private static final String CONTENT_TYPE = "application/json";
	private AsyncParam param;
	private Map<String, String> header;
	private String requestMethod = "POST";

	public JsonCommitEntity(AsyncParam param) {
		// TODO Auto-generated constructor stub
		this.param = param;
	}
	
	public JsonCommitEntity(AsyncParam param ,Map<String, String> header) {
		// TODO Auto-generated constructor stub
		this.param = param;
		this.header = header;
	}
	
	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return CONTENT_TYPE;
	}

	@Override
	public long getContentLengh() {
		// TODO Auto-generated method stub
		return param.jsonParam.getBytes().length;
	}

	@Override
	public void writeTo(OutputStream outputStream) {
		// TODO Auto-generated method stub
		try {
			outputStream.write(param.jsonParam.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, String> getHeader() {
		// TODO Auto-generated method stub
		return this.header;
	}

	@Override
	public String getRequestMethod() {
		// TODO Auto-generated method stub
		return requestMethod;
	}

	@Override
	public boolean isHaveParam() {
		// TODO Auto-generated method stub
		return param !=null;
	}
	
	public void setRequestMethod(String method){
		requestMethod = method;
	}

}
