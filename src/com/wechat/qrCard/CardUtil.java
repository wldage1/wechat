package com.wechat.qrCard;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.wechat.AccessTokenManager;
import com.wechat.message.AccessToken;
import com.wechat.qrCard.entity.ActionInfo;
import com.wechat.qrCard.entity.BaseInfo;
import com.wechat.qrCard.entity.Card;
import com.wechat.qrCard.entity.CardObject;
import com.wechat.qrCard.entity.DateInfo;
import com.wechat.qrCard.entity.GeneralCoupon;
import com.wechat.qrCard.entity.QRCard;
import com.wechat.qrCard.entity.QRCardObject;
import com.wechat.qrCard.entity.Sku;
import com.wechat.util.JedisUtil;
import com.wechat.util.JsonUtil;

public class CardUtil{
	/**
	 * 
	 *  https 请求方法
	 *  @param httpUrl
	 *  @param requestMethod
	 *  @param outputStr
	 *  @return
	 */
	public static String httpRequest(String httpUrl, String requestMethod,
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
			httpUrlConnection.setRequestMethod(requestMethod);
			httpUrlConnection.connect();
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConnection.getOutputStream();
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			InputStream inputStream = httpUrlConnection.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
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
	
	
	public static String createCard() throws ParseException{
		CardObject object = new CardObject();
		Card card = new Card();
		BaseInfo info = new BaseInfo();
		info.setLogo_url("http://t1.qpic.cn/mblogpic/ee8d47b3cefc3a6c25d8/460");
		info.setCode_type("CODE_TYPE_BARCODE");
		info.setBrand_name("手机冲浪嘉年华");
		info.setTitle("30M流量卡");
		info.setSub_title("仅限移动用户使用");
		info.setColor("Color101");
		info.setNotice("");
		info.setService_phone("4001076866");
		info.setSource("");
		info.setUrl_name_type("URL_NAME_TYPE_USE_IMMEDIATELY");
		info.setCustom_url("http://card.orientalwisdom.com/udp/cards/exchange");
		info.setGet_limit(300);
		info.setUse_custom_code(true);
		info.setBind_openid(false);
		info.setCan_share(true);
		info.setCan_give_friend(true);
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date myDate2 = dateFormat2.parse("2015-02-01 00:00:00");
		long  begin_timestamp = myDate2.getTime()/1000;
		
		Date myDate3 = dateFormat2.parse("2015-06-30 00:00:00");
		long  end_timestamp = myDate3.getTime()/1000;
		
		DateInfo di = new DateInfo();
		di.setType(1);//2是按天算有效期,1是按固定日期
		di.setBegin_timestamp(begin_timestamp);
		di.setEnd_timestamp(end_timestamp);
		info.setDate_info(di);
		
		Sku sku = new Sku();
		sku.setQuantity(5000000);
		info.setSku(sku);
		
		
		info.setDescription("【转赠功能】：点击卡券右上方“赠送”可将卡券转发给微信好友使用。\n"
				+ "【充值说明】：在卡券下方点击“立即使用”后可进入充流量页面，输入手机号码、选择生效时间后点击按钮即充流量。\n"
				+ "【生效时间】：当月生效即实时充值，流量包在本月有效，下月生效会在下个月1日自动为您充值，流量包在下月有效。\n"
				 );
		
		GeneralCoupon gc = new GeneralCoupon();
		//优惠详情
		gc.setDefault_detail(
				"【使用范围】：北京移动\n"
				+ "【截止日期】：2015-6-30\n"
				+ "【使用次数】：本卡券只限使用一次\n"
				+ "【温馨提示】：当您充值成功后会接收到运营商发出的确认短信，"
				+ "同时卡券将从微信卡包中核销；当您充值失败时，"
				+ "请检查输入手机号码输入是否正确，保证该手机号码未被停机，"
				+ "手机网络在服务区。如您对本业务相关事宜有争议，"
				+ "请您进入微信公众号自助查询或在线客服解决与处理。"
			);
		
		
		gc.setBase_info(info);
		card.setCard_type("GENERAL_COUPON");
		card.setGeneral_coupon(gc);
		object.setCard(card);
		System.out.println(JsonUtil.toJson(object));
		String url = "https://api.weixin.qq.com/card/create?access_token=ACCESS_TOKEN";
		
		 
		url= url.replace("ACCESS_TOKEN",JedisUtil.getToken());
		String resultCode = CardUtil.httpRequest(url, "POST", JsonUtil.toJson(object));
		System.out.println(resultCode);
		return resultCode;
	}
	
	
	/**
	 * 
	 * 	获取卡券的二维码-码值
	 */
	public static void createQRCode(){
		String url = "https://api.weixin.qq.com/card/qrcode/create?access_token=TOKEN";
		
		String cardId = "pRMqosyvTfaGlrMOfOr8eFHdSXmE";
		String openId = "";
		QRCardObject object = new QRCardObject();
		QRCard card = new QRCard(); 
		ActionInfo ai = new ActionInfo();

		card.setCard_id(cardId);
		card.setCode("I123kQ91237/LL6vkq");
//		card.setOpenid(openId);
		card.setIs_unique_code(false);
		
		ai.setCard(card);
		object.setAction_name("QR_CARD");
		object.setAction_info(ai);
		AccessToken token = AccessTokenManager.getAccessToken("wx137810a045c9a71e", "7d546ebbcb3e0acefde754c793b92cc0");
		url= url.replace("TOKEN", token.getAccess_token());
		String resultCode = CardUtil.httpRequest(url, "POST", JsonUtil.toJson(object));
		
		System.out.println(resultCode);
	}
	
	public static void main(String[] args) throws ParseException {
//		createQRCode();
		createCard();
		
//		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
//		
//		AccessToken token = AccessTokenManager.getAccessToken("wx137810a045c9a71e", "7d546ebbcb3e0acefde754c793b92cc0");
////		x5PuutzTokbh6E2dfQT3M5FSD8h8NnTOwDErgM2zlD-oZ9gvhA7PofncKCkqjupodMPcUbqur8L3TqnLGqwEo05qewtGj85rrHRGvQs9OFo
//		String ticket = "sM4AOVdWfPE4DxkXGEs8VGmlgmF7odGcZWd2mRT5xLSwf5mRGWFJqYqJIwzHIeY5vQXENqY0Eg03rY1MFw9RHg";
//		
//		url = url.replace("ACCESS_TOKEN", token.getAccess_token());
//		String resultCode = CardUtil.httpRequest(url, "GET",null);
//		
//		System.out.println(resultCode);
		
		
//		String tokenStr = "7R28z6QLixe97ZIB9qTbTCmEpOiuQJ80GjfGH19ZiX0cWXaCOa118qfYvXAlUPOZ_MwT-YQkPAXRiuUbaTGQTzOyfOCt3jYuiyxSmDtRTi4";
//		String ticket = "E0o2-at6NcC2OsJiQTlwlLAUcPIk1y3iCHNygy3q8IRkYGGdSF_VbQkZDLD--imjO2-jqES4QNBg98lNn13shg";
//		System.out.println(APITicketManager.getTicket(token.getAccess_token()));
		
	}
	
	
	/**
	 * 
	 *  获取二维码卡券
	 *  @param ticket
	 *  @return
	 */
	public static String getQR(String ticket){
		
		String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
		url = url.replace("TICKET", ticket);
		return url;
	}
	
}
