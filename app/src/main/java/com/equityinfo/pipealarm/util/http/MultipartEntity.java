package com.equityinfo.pipealarm.util.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * @author lxs
 *	混合提交方式
 */
public class MultipartEntity implements AsyncInter{
	private String boundary = java.util.UUID.randomUUID().toString();
    private static final String prefix = "--", linend = "\r\n";
    private static final String MULTIPART_FROM_DATA = "multipart/form-data";
    private static final String CHARSET = "UTF-8";
	private AsyncParam param = null;
	private Map<String, String> header = null;
	private String requestMethod = "POST";

	public MultipartEntity(AsyncParam param) {
		// TODO Auto-generated constructor stub
		this.param = param;
	}
	
	public MultipartEntity(AsyncParam param , Map<String, String> header) {
		// TODO Auto-generated constructor stub
		this.param = param;
		this.header = header;
	}
	
	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return MULTIPART_FROM_DATA + ";boundary=" + boundary;
	}

	@Override
	public long getContentLengh() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeTo(OutputStream outputStream) {
		// TODO Auto-generated method stub
		  StringBuilder sb = new StringBuilder();
	        for (Map.Entry<String, String> entry : param.urlParam.entrySet())
	        {
	            sb.append(prefix);
	            sb.append(boundary);
	            sb.append(linend);
	            sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + linend);
	            sb.append("Content-Type: text/plain; charset=" + CHARSET + linend);
	            sb.append("Content-Transfer-Encoding: 8bit" + linend);
	            sb.append(linend);
	            sb.append(entry.getValue());
	            sb.append(linend);
	        }
	        try {
				outputStream.write(sb.toString().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        for (Map.Entry<String, File> file : param.fileParam.entrySet())
            {
                StringBuilder sb1 = new StringBuilder();
                sb1.append(prefix);
                sb1.append(boundary);
                sb1.append(linend);
                // name是post中传参的键 filename是文件的名称
                sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getKey() + "\"" + linend);
                sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET + linend);
                sb1.append(linend);
                try {
					outputStream.write(sb1.toString().getBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

                InputStream is;
				try {
					is = new FileInputStream(file.getValue());
					byte[] buffer = new byte[1024];
	                int len = 0;
	                while ((len = is.read(buffer)) != -1)
	                {
	                	outputStream.write(buffer, 0, len);
	                }

	                is.close();
	                outputStream.write(linend.getBytes());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				byte[] end_data = (prefix + boundary + prefix + linend).getBytes();
				try {
					outputStream.write(end_data);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
            }
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
	
	public void setRequestMethod(String method){
		requestMethod = method;
		
	}

}
