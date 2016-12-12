package com.equityinfo.pipealarm.util.http;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

/**
 * @author lxs
 * 表单提交方式
 */
public class FormCommitEntity implements AsyncInter{
	private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
	private AsyncParam param = null;
	private Map<String, String> header = null;
	private String requestMethod = "POST";
	
	public FormCommitEntity(AsyncParam param) {
		// TODO Auto-generated constructor stub
		this.param = param;
	}
	public FormCommitEntity(AsyncParam param,Map<String, String> header) {
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
		return param.urlParamToString().getBytes().length;
	}

	@Override
	public Map<String, String> getHeader() {
		// TODO Auto-generated method stub
		return header;
	}

	@Override
	public String getRequestMethod() {
		// TODO Auto-generated method stub
		return requestMethod;
	}
	@Override
	public boolean isHaveParam() {
		// TODO Auto-generated method stub
		return param != null;
	}
	@Override
	public void writeTo(OutputStream outputStream) {
		// TODO Auto-generated method stub
		try {
			outputStream.write(param.urlParamToString().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setRequestMethod(String method){
		requestMethod = method;
		
	}
	
}
