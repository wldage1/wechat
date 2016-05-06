package com.wechat.jsapi;

import com.wechat.AccessTokenManager;
import com.wechat.util.WechatHttpUtil;

public class JSAPIUtil {
	
	public static String getJSAPIStr()  {
		String token = AccessTokenManager.getToken();
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
		url = url.replace("ACCESS_TOKEN", token);
		return WechatHttpUtil.httpRequest(url, "GET", null);
	}

}
