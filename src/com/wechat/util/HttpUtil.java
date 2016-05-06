package com.wechat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.wechat.message.AccessToken;

import net.sf.json.JSONObject;

public class HttpUtil {
	
	/**
	 * HTTPS GET请求
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String httpsGet(String url) throws IOException{
		URL requestURL = new URL(url);
		HttpsURLConnection httpsConnection = (HttpsURLConnection)requestURL.openConnection();
		httpsConnection.setUseCaches(false);
		httpsConnection.setRequestMethod("GET");
		
		// 将返回的输入流转换成字符串
		InputStream inputStream = httpsConnection.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
					
		StringBuffer stringBuffer=new StringBuffer();
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			stringBuffer.append(line);
		}
		inputStreamReader.close();
		bufferedReader.close();
		httpsConnection.disconnect();
		
		return stringBuffer.toString();
	}
	
	/**
	 * HTTPS POST请求
	 * @param url
	 * @param postStr
	 * @return
	 * @throws IOException
	 */
	public static String httpsPost(String url, String postStr) throws IOException{
		URL requestURL = new URL(url);
		HttpsURLConnection httpsConnection = (HttpsURLConnection)requestURL.openConnection();
		
		httpsConnection.setDoInput(true);
		httpsConnection.setDoOutput(true);
		httpsConnection.setUseCaches(false);
		httpsConnection.setRequestMethod("POST");
		httpsConnection.setRequestProperty("Content-Length", String.valueOf(postStr.getBytes().length));
		httpsConnection.getOutputStream().write(postStr.getBytes());
		httpsConnection.getOutputStream().flush();
		httpsConnection.getOutputStream().close();
		
		InputStreamReader inputStreamReader=new InputStreamReader(httpsConnection.getInputStream());
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		StringBuffer stringBuffer=new StringBuffer();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			stringBuffer.append(line);
		}
		inputStreamReader.close();
		bufferedReader.close();
		httpsConnection.disconnect();
		
		return stringBuffer.toString();
	}	
	
	/**
	 * 获得token
	 * @author wang.l
	 * @date 2016年4月20日
	 * @return String
	 */
	@SuppressWarnings("static-access")
	public static String getAccessTokenJson() throws IOException{
		// 第三方用户唯一凭证  
		String appId = SystemProperty.getInstance("config").getProperty("Wechat.appID");
        // 第三方用户唯一凭证密钥  
		String appSecret = SystemProperty.getInstance("config").getProperty("Wechat.appSecret");
		
		String requestURL = String.format(Contact.ACCESS_TOKEN_URL, appId, appSecret);
		
		JSONObject token = JSONObject.fromObject(httpsGet(requestURL));

		AccessToken accessToken = (AccessToken) token.toBean(token, AccessToken.class);
		
		return accessToken.getAccess_token();
	}
	
}
