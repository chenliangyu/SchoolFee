package org.school.fee.support.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMSUtils {
	private static Logger logger = LoggerFactory.getLogger(SMSUtils.class);
	private static final String addr = "http://api.sms.cn/mtutf8/";
	private static final String userId = "binavid";
	private static final String pwd = "ecc993bdae99bc911493ffc5f8b64b0d";  
	private static final String encode = "utf8";  

	
	public static void sendSMS(List<String> phones,String msg){
		for(String phone : phones){
			sendSMS(phone, msg);
		}
	}
	public static void sendSMS(String phone,String msg){
		logger.debug("send a sms message to {},the content is {}",phone,msg);
		//组建请求
		PrintWriter out = null;
		BufferedReader in = null;
		try{
			String straddr = addr; 
			StringBuilder params = new StringBuilder();
			params.append("uid="+userId);
			params.append("&pwd="+pwd);
			params.append("&mobile="+phone);
			params.append("&encode="+encode);
			params.append("&content=" + java.net.URLEncoder.encode(msg,"utf-8"));
			String param = params.toString();
			logger.debug("url:{}",straddr);
			logger.debug("param:{}",param);
			//发送请求
			URL url = new URL(straddr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			out = new PrintWriter(connection.getOutputStream());
			out.write(param);
			out.flush();
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			
			//返回结果
			String inputline = in.readLine();
			logger.debug("Response:"+inputline);
		}catch(IOException e){
			logger.error("打开输出流出错:{}",e.getMessage());
		}finally{
			try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
			}catch(IOException e){
				logger.error("关闭输出流出错:{}",e.getMessage());
            }
		}
	}
}
