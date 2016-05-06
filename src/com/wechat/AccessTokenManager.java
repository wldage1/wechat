package com.wechat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wechat.message.AccessToken;
import com.wechat.util.Contact;
import com.wechat.util.JedisUtil;
import com.wechat.util.MyX509TrustManager;

import net.sf.json.JSONObject;


/**
 * AccessToken服务类,主要用来获取腾讯AccessToken，具体Token是什，
 * 参照http://mp.weixin.qq.com/wiki/index.php?title=获取access_token
 * 
 */
public class AccessTokenManager {

	private static Logger log = LoggerFactory.getLogger(AccessTokenManager.class);

	/**
	 * 
	 * 获取 微信全局唯一票据凭证</br>
	 * 
	 * appId appSecret ,这两个参数 腾讯公众账号开启开发者模式后获取的。 </br>
	 * 具体参照：http://mp.weixin.qq.com/wiki/index.php?title=接入指南
	 * 
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static AccessToken getAccessToken(String appId, String appSecret) {

		// 获取token的接口地址
		String requestUrl = String.format(Contact.ACCESS_TOKEN_URL, appId, appSecret);

		StringBuffer buffer = new StringBuffer();
		// token 对象
		AccessToken accessToken = null;
		JSONObject jsonObject = null;

		try {
			// 调用腾讯接口 获取token 采用Get请求方式
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslConetext = SSLContext.getInstance("SSL", "SunJSSE");
			sslConetext.init(null, tm, new java.security.SecureRandom());
			SSLSocketFactory ssf = sslConetext.getSocketFactory();
			conn.setSSLSocketFactory(ssf);
			conn.setDoInput(true);
			// 设置请求方式
			conn.setRequestMethod("GET");

			// 取得输入流
			InputStream inputStream = conn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			System.out.println("----------  " + buffer.toString() + "   ----------  ");
			// 关闭 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			conn.disconnect();

			jsonObject = JSONObject.fromObject(buffer.toString());

			accessToken = (AccessToken) jsonObject.toBean(jsonObject, AccessToken.class);

		} catch (Exception e) {
			log.error("get accessToken is error:{}", e);
		}

		return accessToken;
	}

	/**
	 * 
	 * 获取AccessToken
	 * 
	 * @return
	 */
	public static String getToken() {
		String tokenStr = null;
		try {
			tokenStr = JedisUtil.getToken();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("get accessToken is error:{}", e);
		}
		return tokenStr;
	}

	/**
	 * 
	 * 刷新AccessToken 数据 <br>
	 * 目前 腾讯规定的token有效期为7200秒</br>
	 * 
	 * @param token
	 * @param expriesIn 有效时间 单位 秒
	 */
	@SuppressWarnings("deprecation")
	public static void setToken(String token, String expriesIn) {
		try {
			SAXReader sax = new SAXReader();

			String filePath = URLDecoder.decode(AccessTokenManager.class.getResource("/AccessToken.xml").getFile());
			File file = new File(filePath);
			// 解析xml
			Document doc = sax.read(new FileInputStream(file));

			// 获取跟目录
			Element root = doc.getRootElement();
			// 获取子节点
			Element tokenElement = root.element("token");
			Element expiresInElement = root.element("expires_in");
			// 重写数据
			tokenElement.setText(token);
			expiresInElement.setText(expriesIn);
			// 写入xml
			XMLWriter output = new XMLWriter(new FileWriter(file));
			output.write(doc);
			// 关闭流
			output.close();

		} catch (Exception e) {
			log.error("setToken is error {}", e);
		}

	}

	 public static void main(String[] args) {
		// 获取token的接口地址
		String requestUrl = String.format(Contact.ACCESS_TOKEN_URL, "aaaa", "bbbbbb");
		System.out.println(requestUrl);
	}
}
