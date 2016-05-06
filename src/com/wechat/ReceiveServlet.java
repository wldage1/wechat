package com.wechat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wechat.message.WechatMsg;
import com.wechat.message.req.Text;
import com.wechat.message.req.TextMessage;
import com.wechat.util.JsonUtil;
import com.wechat.util.WechatHttpUtil;

/**
 * 
 * 微信服务下行接口,接收请求信息后，发送微信消息
 * 
 */
public class ReceiveServlet {

	private static Logger log = LoggerFactory.getLogger(ReceiveServlet.class);

	/**
	 * 
	 * 发送微信下行消息
	 * 
	 * @param msgBean
	 * @return
	 * @throws Exception
	 */
	public static WechatMsg sendMessage(String openId, String message) throws Exception {
		String token = AccessTokenManager.getToken();
		WechatMsg wechatMsg = null;

		TextMessage t = new TextMessage();
		t.setMsgtype("text");
		Text ts = new Text();
		ts.setContent(message);
		t.setText(ts);
		t.setTouser(openId);

		// 响应状态
		wechatMsg = WechatHttpUtil.sendTextMessage(t, token);

		log.info("===========  腾讯返回状态   <文本消息>  ================");
		log.info(JsonUtil.toJson(wechatMsg));
		log.info("===========  end  ================");

		System.out.println("===========  腾讯返回状态   <文本消息>  ================");
		System.out.println(JsonUtil.toJson(wechatMsg));
		System.out.println("===========  end  ================");

		return wechatMsg;
	}
}
