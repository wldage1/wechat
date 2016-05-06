package com.wechat;

import com.wechat.qrCard.CardUtil;

public class APITicketManager {
	
	/**
	 * 
	 *  api_ticket 是用于调用微信 JSAPI 的临时票据，有效期为 7200 秒，
	 *  通过 access_token 来获取。
	 *  @param token
	 *  @return
	 */
	public static String getTicket(String token){
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=wx_card";
		url = url.replace("ACCESS_TOKEN", token);
		String resultCode = CardUtil.httpRequest(url, "GET", null);
		System.out.println(resultCode);
		return resultCode;
	}
}
