package com.equityinfo.pipealarm.util.http;

import java.io.OutputStream;
import java.util.Map;

public interface AsyncInter {
	
    public String getContentType();
    
    public long getContentLengh();

	public void writeTo(OutputStream outputStream);
	
	public Map<String, String> getHeader();
	
	public String getRequestMethod();
	
	public boolean isHaveParam();
	
	public void setRequestMethod(String method);
}
