package com.wechat.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wechat.message.WechatMsg;
import com.wechat.message.req.ImageMessage;
import com.wechat.message.req.TextMessage;
import com.wechat.message.resp.MediaMessage;

/**
 * 
 * 微信服务接口工具类
 * 
 */
public class WechatHttpUtil {
	private static Logger log = LoggerFactory.getLogger(WechatHttpUtil.class);


	public static String http(String httpUrl, String requestMethod,
			String outputStr) {
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(httpUrl);
			URLConnection rulConnection = url.openConnection();
			HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
			httpUrlConnection.setDoOutput(true);
			httpUrlConnection.setDoInput(true);
			httpUrlConnection.setUseCaches(false);
			httpUrlConnection.setRequestProperty("Content-Type", "application/json");
			httpUrlConnection.setRequestProperty("POST", "/violations HTTP/1.1");
			httpUrlConnection.setRequestProperty("ContentType","text/xml;charset=utf-8"); 
			httpUrlConnection.setRequestMethod(requestMethod);
			httpUrlConnection.connect();
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConnection.getOutputStream();
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			InputStream inputStream = httpUrlConnection.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			System.out.println(buffer);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
	
	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static String httpRequest(String requestUrl, String requestMethod,
			String outputStr) {
		StringBuffer buffer = new StringBuffer();
		try {

			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());

			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);

			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();

			log.info("===============   发送请求消息到微信接口  ===================");
			log.info(buffer.toString());
			log.info("===============  发送请求消息到微信接口 end  ===================");

			System.out.println("===============   发送请求消息到微信接口  ===================");
			System.out.println(buffer.toString());
			System.out.println("===============  发送请求消息到微信接口 end  ===================");

		} catch (ConnectException ce) {
			ce.printStackTrace();
			log.error("----------  发送请求消息到微信接口，链接超时       ----------", ce);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("-----------  发送请求消息到微信接口 ，http请求失败 :-----------,{}", e);
		}
		return buffer.toString();
	}

	/**
	 * 通过http请求上传媒体文件到微信服务器
	 * 
	 * @param accessToken
	 *            微信服务接口全局唯一票据
	 * @param type
	 *            上传媒体类型
	 * @param mediaFileUrl
	 *            文件路径
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static MediaMessage uploadMedia(String accessToken, String type,
			String mediaFileUrl) {

		StringBuffer buffer = new StringBuffer();

		MediaMessage mediaMessage = null;

		JSONObject jsonObject = null;

		// 拼装请求地址
		String uploadMediaUrl = SystemProperty.getInstance("config").getProperty("uploadMediaUrl");

		uploadMediaUrl = uploadMediaUrl.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);

		// 定义数据分隔符
		String boundary = "------------7da2e536604c8";

		try {

			URL uploadUrl = new URL(uploadMediaUrl);
			HttpURLConnection uploadConn = (HttpURLConnection) uploadUrl.openConnection();
			uploadConn.setDoOutput(true);
			uploadConn.setDoInput(true);
			uploadConn.setRequestMethod("POST");
			// 设置请求头Content-Type
			uploadConn.setRequestProperty("Content-Type","multipart/form-data;boundary=" + boundary);

			OutputStream outputStream = uploadConn.getOutputStream();

			URL mediaUrl = new URL(mediaFileUrl);
			HttpURLConnection mediaConn = (HttpURLConnection) mediaUrl.openConnection();
			mediaConn.setDoInput(true);
			mediaConn.setRequestMethod("GET");

			if (!checkImageUrl(mediaFileUrl)) {
				mediaMessage = new MediaMessage();
				mediaMessage.setErrcode("" + mediaConn.getResponseCode());
				mediaMessage.setErrmsg(mediaConn.getResponseMessage());
				mediaConn.disconnect();
				uploadConn.disconnect();
				outputStream.close();
				return mediaMessage;
			}

			// 从请求头中获取内容类型
			String contentType = mediaConn.getHeaderField("Content-Type");
			// 根据内容类型判断文件扩展名
			String fileExt = getFileExt(contentType);

			// ----请求体开始
			outputStream.write(("--" + boundary + "\r\n").getBytes());
			outputStream.write(String.format("Content-Disposition: form-data;name=\"media\";filename=\"file1%s\"\r\n", fileExt).getBytes());
			outputStream.write(String.format("Content-Type: %s\r\n\r\n", contentType).getBytes());

			// 获取媒体文件的输入流（读取文件）
			BufferedInputStream bis = new BufferedInputStream(mediaConn.getInputStream());
			byte[] buf = new byte[8096];
			int size = 0;
			while ((size = bis.read(buf)) != -1) {
				// 将媒体文件写到输出流(往微信服务器写数据)
				outputStream.write(buf, 0, size);
			}

			// ----请求体结束
			outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());

			// 释放资源
			outputStream.close();
			bis.close();
			mediaConn.disconnect();

			// 获取媒体文件上传到输入流(从微信服务器读数据)
			InputStream inputStream = uploadConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			// 释放资源
			bufferedReader.close();
			inputStream = null;
			uploadConn.disconnect();

			jsonObject = JSONObject.fromObject(buffer.toString());

			mediaMessage = (MediaMessage) jsonObject.toBean(jsonObject,MediaMessage.class);

			// --日志
			log.info("================ 上传图片到微信   ====================");
			log.info(buffer.toString());
			log.info("================ 上传图片到微信 end   ====================");

			System.out.println("================ 上传图片到微信   ====================");
			System.out.println(buffer.toString());
			System.out.println("================ 上传图片到微信 end   ====================");

		} catch (Exception e) {

			buffer = null;
			mediaMessage = null;
			jsonObject = null;
			e.printStackTrace();
			log.error("上传媒体文件失败:{}", e);
		}

		return mediaMessage;
	}

	/**
	 * 校验图片路径的合法性
	 * 
	 * @param filePath
	 * @return
	 */
	@SuppressWarnings("static-access")
	private static boolean checkImageUrl(String filePath) {

		URL mediaUrl;
		HttpURLConnection mediaConn;
		try {
			mediaUrl = new URL(filePath);
			mediaConn = (HttpURLConnection) mediaUrl.openConnection();

			if (mediaConn.HTTP_NOT_FOUND == mediaConn.getResponseCode()) {
				mediaConn.disconnect();
				mediaUrl = null;
				return false;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			log.error("checkImageUrl error,{}", e);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("checkImageUrl error,{}", e);
		}

		return true;
	}

	/**
	 * 下载多媒体文件
	 * 
	 * @param accessToken
	 *            接口访问凭证
	 * @param media_Id
	 *            媒体文件标识
	 * @param savePath
	 *            文件中服务器上的存储路径
	 * @return 下载多媒体文接口不支持下载视频文件，只支持下载图片、语音和缩略图文件。
	 */
	public static String getMedia(String accessToken, String mediaId,
			String savePath) {
		String filePath = null;

		// 拼接请求地址
		String requestUrl = SystemProperty.getInstance("config").getProperty("downloadMediaUrl");
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
		try {

			// 设置请求
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");

			if (!savePath.endsWith("/")) {
				savePath += "/";
			}

			// 根据内容类型获取扩展名
			String fileExt = getFileExt(conn.getHeaderField("Content-Type"));
			// 将media作为文件名
			filePath = savePath + mediaId + fileExt;

			BufferedInputStream bis = new BufferedInputStream(
					conn.getInputStream());
			FileOutputStream fos = new FileOutputStream(new File(filePath));

			byte[] buf = new byte[8096];
			int size = 0;
			while ((size = bis.read(buf)) != -1) {
				fos.write(buf, 0, size);
			}

			// 释放资源
			fos.close();
			bis.close();
			conn.disconnect();
			log.info("=========   下载媒体文件成功   ============");
			log.info(filePath);
			log.info("=========   下载媒体文件end    ============");

			System.out.println("=========   下载媒体文件成功   ============");
			System.out.println(filePath);
			System.out.println("=========     下载媒体文件 end    ============");

		} catch (Exception e) {
			filePath = null;
			e.printStackTrace();
			log.error("下载媒体文件失败:{}", e);
		}

		return filePath;
	}

	/***
	 * 
	 * 上传图片到FTP
	 * 
	 * @param accessToken
	 * @param mediaId
	 * @return
	 */
	public static String uploadImgToFTP(String accessToken, String mediaId) {

		String filePath = null;

		// 拼接请求地址
		String requestUrl = SystemProperty.getInstance("config").getProperty("downloadMediaUrl");
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
		try {

			// 设置请求
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");

			// 根据内容类型获取扩展名
			String fileExt = getFileExt(conn.getHeaderField("Content-Type"));
			// 文件名
			String fileId = new SimpleDateFormat("yyyyMMddHHmmssSSS")
					.format(new Date())
					+ new Random().nextInt(10000)
					+ new Random().nextInt(10000);

			String fileName = fileId + fileExt;

			// 调用FTP对象上传图片
			filePath = FTPUtil.uploadFile(conn.getInputStream(),
					SystemProperty.getInstance("config").getProperty("FTP_IMG_FOLDER"), fileName);
			// 释放资源
			conn.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("upload to ftp failed :{}", e);
		}

		return filePath;
	}

	/**
	 * 发送文本消息到腾讯微信
	 * 
	 * @param textMessage
	 *            文本消息实例
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static WechatMsg sendTextMessage(TextMessage textMessage,
			String accessToken) {

		WechatMsg msg = null;

		String url = SystemProperty.getInstance("config").getProperty("customSend").replace(
				"ACCESS_TOKEN", accessToken);
		try {

			// 将文本消息对象转换成json字符串
			String jsonTextMessage = JSONObject.fromObject(textMessage)
					.toString();
			// 调用微信http接口接口
			
			String jsonStr = http(url, "POST", jsonTextMessage);

			msg = JsonUtil.toBean(jsonStr, WechatMsg.class);

			// --日志
			log.info("============ 发送文本消息  ==============");
			log.info("token:" + accessToken);
			log.info("jsonTextMessage:" + jsonTextMessage);
			log.info("============ 发送文本消息  end ==============");

			System.out.println("============ 发送文本消息  ==============");
			System.out.println("token:" + accessToken);
			System.out.println("jsonTextMessage:" + jsonTextMessage);
			System.out.println("============ 发送文本消息  end ==============");

		} catch (Exception e) {
			msg = null;
			e.printStackTrace();
			log.error("发送腾讯微信下行文本消息失败 : {}", e);
		}
		return msg;
	}

	/**
	 * 发送图片消息到腾讯微信接口
	 * 
	 * @param imageMessage
	 *            图片消息对象
	 * @param accessToken
	 *            腾讯微信全局唯一票据凭证.
	 * @return
	 */
	public static WechatMsg sendImgMessage(ImageMessage imageMessage,
			String accessToken) {
		// 拼装创建菜单的url
		String url = SystemProperty.getInstance("config").getProperty("customSend").replace(
				"ACCESS_TOKEN", accessToken);
		WechatMsg msg = null;
		try {

			// 对象转换成json字符串
			String jsonImageMessage = JSONObject.fromObject(imageMessage)
					.toString();

			System.out.println(jsonImageMessage);
			// 调用发送图片消息的接口
			String jsonStr = httpRequest(url, "POST", jsonImageMessage);

			msg = JsonUtil.toBean(jsonStr, WechatMsg.class);
			// --日志
			log.info("============ 发送文本消息  ==============");
			log.info("token:" + accessToken);
			log.info("jsonTextMessage:" + jsonImageMessage);
			log.info("============ 发送文本消息  end ==============");

			System.out.println("============ 发送文本消息  ==============");
			System.out.println("token:" + accessToken);
			System.out.println("jsonImageMessage:" + jsonImageMessage);
			System.out.println("============ 发送文本消息  end ==============");

		} catch (Exception e) {
			msg = null;
			log.error("发送腾讯微信下行图片消息失败: {}", e);

		}
		return msg;
	}

	/**
	 * 验证签名
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 *            与腾讯开发者模式接口配置信息中的Token要一致，不懂去看 //
	 *            http://mp.weixin.qq.com/wiki/index.php?title=接入指南
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp,
			String nonce) {

		String[] arr = new String[] { SystemProperty.getInstance("config").getProperty("token"),
				timestamp, nonce };
		System.out.println("token:" + SystemProperty.getInstance("config").getProperty("token"));
		// 将token、timestamp、nonce三个参数进行字典序排序
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		content = null;
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];

		String s = new String(tempArr);
		return s;
	}

	/**
	 * 计算采用utf-8编码方式时字符串所占字节数
	 * 
	 * @param content
	 * @return
	 */
	public static int getByteSize(String content) {
		int size = 0;
		if (null != content) {
			try {
				// 汉字采用utf-8编码时占3个字节
				size = content.getBytes("utf-8").length;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return size;
	}

	/**
	 * 根据内容类型判断文件扩展名
	 * 
	 * @param contentType
	 * @return
	 */
	public static String getFileExt(String contentType) {
		String fileExt = "";

		if (contentType.indexOf("image/jpeg") >= 0) {
			fileExt = ".jpg";
		}
		if (contentType.indexOf("audio/mpeg") >= 0) {
			fileExt = ".mp3";
		}
		if (contentType.indexOf("video/pm4") >= 0) {
			fileExt = ".mp4";
		}
		if (contentType.indexOf("video/mpeg4") >= 0) {
			fileExt = ".mp4";
		}

		return fileExt;
	}

}
