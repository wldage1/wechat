package com.wechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wechat.des.Des;
import com.wechat.qrCard.entity.CardEvent;
import com.wechat.ucs.Message;
import com.wechat.ucs.UcsWechatService;
import com.wechat.util.JsonUtil;
import com.wechat.util.MessageUtil;
import com.wechat.util.SystemProperty;
import com.wechat.util.WechatHttpUtil;
/**
 * 
 * 微信核心服务类
 * 
 */
public class WechatService {

	private static Logger log = LoggerFactory.getLogger(WechatService.class);

	/**
	 * 解析腾讯微信请求信息.
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		String strNull = "";
		try {
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// // 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			
			 // TODO 发送消息到UCS
            Message msg = new Message();
            msg.setAccessType("wechat");
            msg.setSender(fromUserName);
            msg.setReceiver(toUserName);
            msg.setMessageType(msgType);
            //UCS接受地址
            String url = SystemProperty.getInstance("config").getProperty("ucs.url")+"/sender/wechat/message";
            
			String message;
			
			// 推送事件
			if ("event".equals(msgType)) {
				String event = requestMap.get("Event");
				// 卡券领取事件（包括转赠）
				if ("user_get_card".equals(event)) {
					cardEvent(requestMap, false);
				}
				// 删除卡券事件推送
				if ("user_del_card".equals(event)) {
					cardEvent(requestMap, true);
				}
				// 菜单点击事件
				if ("CLICK".equals(event)) {
					String eventKey = requestMap.get("EventKey");
					// 体验区
					if ("EXPERIENCE_0_0".equals(eventKey)) {
						StringBuilder sBuf = new StringBuilder();
						sBuf.append("亲们好，MUA！\n");
						sBuf.append("流小羊正在为您准备丰盛的流量服务大餐：\n");
						sBuf.append("嘉华专属移动、联通、电信微信流量卡；\n");
						sBuf.append("嘉华微信流量全网通系列；\n");
						sBuf.append("嘉华流量极速充；\n");
						sBuf.append("嘉华微信流量红包；\n");
						sBuf.append("嘉华……此处省略1万字；\n");
						sBuf.append("敬请期待哦！：）");
						ReceiveServlet.sendMessage(fromUserName, sBuf.toString());
					}
					
					// 客服
					if ("CUSTOMER_SERVICE".equals(eventKey)) {
						message = "各位亲！流小羊还在实习中，老板让俺春节期间上岗，想吐槽的就给俺留言~~~";
						ReceiveServlet.sendMessage(fromUserName, message);
					}
				}
				// 关注事件
				if ("subscribe".equals(event)) {
				    message = "亲，欢迎你关注测试账号~~欢迎噢~~";
                    ReceiveServlet.sendMessage(fromUserName, message);
                    //保存微信客户信息
                    UcsWechatService.sendWechatUserInfoToUcs(event, msg, url, fromUserName);
					return strNull;
				}
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                //文本消息
                msg.setContent(requestMap.get("Content"));
                String content = JsonUtil.toJson(msg);
                WechatHttpUtil.http(url, "POST",  content);
                return strNull;
            }else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                // 图片信息
                msg.setMediaId(requestMap.get("MediaId"));
                 //上传图片到ftp服务器
                String imgFTPUrl= WechatHttpUtil.uploadImgToFTP(AccessTokenManager.getToken(), requestMap.get("MediaId"));
                // 图片访问地址
                msg.setPicUrl(imgFTPUrl);
                String content = JsonUtil.toJson(msg);
                WechatHttpUtil.http(url, "POST",  content);
                return strNull;
            }
		} catch (Exception e) {
			log.error("接收消息失败:{}", e);
			e.printStackTrace();
		}

		return strNull;
	}
	
    public static String readInputStream2Str(InputStream inStream, String charset) throws IOException {
        StringBuilder strBuilder = new StringBuilder();
        BufferedReader reader = null;
        try {
            // 读取响应
            reader = new BufferedReader(new InputStreamReader(inStream, charset));
    
            String lines;
            while ((lines = reader.readLine()) != null) {
                strBuilder.append(lines);
            }
            
            if (inStream != null) {
                inStream.close();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("BUS_读取IO输入流到String失败！");
            throw e;
        }

        return strBuilder.toString();
    }
    
	/**
	 *  转赠
	 *  @param requestMap
	 *  @author langkai
	 *  @version 1.0
	 *  </pre>
	 *  Created on :2015年1月28日 下午5:05:14
	 *  LastModified:
	 *  History:
	 *  </pre>
	 * @throws Exception 
	 */
	private static void cardEvent(Map<String, String> requestMap, boolean isDelCard) throws Exception {
		// 转赠
		System.out.println("================== cardEvent Start ==================");
		CardEvent cardEvent = new CardEvent();
		if (!isDelCard) {
			// 赠送方账号
			cardEvent.setOldOpenId(requestMap.get("FriendUserName"));
			// 领券方帐号
			cardEvent.setOpenId(requestMap.get("FromUserName"));
			// 是否为转赠，1 代表是，0 代表否
			cardEvent.setIsGiveByFriend(requestMap.get("IsGiveByFriend"));
			// event
			cardEvent.setEvent("get");
		} else {
			// event
			cardEvent.setEvent("del");
		}
		// cardId
		cardEvent.setCardId(requestMap.get("CardId"));
		// cardCode
		cardEvent.setCardCode(requestMap.get("UserCardCode"));
		
		String jsonStr = JsonUtil.toJson(cardEvent);
		
		// 加密输入报文
		jsonStr = Des.encryptDES(jsonStr, SystemProperty.getInstance("config").getProperty("encrypt.key"));
		
		// 发送http请求
//		WechatHttpUtil.httpConnRequest("http://localhost:8080/udp/cards/event", "POST", jsonStr);
		WechatHttpUtil.http(SystemProperty.getInstance("config").getProperty("card.event.url"), "POST", jsonStr);
		System.out.println("================== " + jsonStr);
		System.out.println("================== cardEvent End ==================");
	}

	public static String formatTime(String createTime) {
		// 将微信传入的CreateTime转换成long类型，再乘以1000
		long msgCreateTime = Long.parseLong(createTime) * 1000L;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date(msgCreateTime));
	}

	public static void main(String[] args) throws Exception {
        // 转赠
//      Map<String, String> requestMap = new HashMap<String, String>();
//      requestMap.put("FromUserName", "oUZWKs4q7QQ_PBRMe-saltC-rh14");
//      requestMap.put("FriendUserName", "ffff");
//      requestMap.put("IsGiveByFriend", "1");
//      requestMap.put("CardId", "pUZWKs1MEaImYY0unx7LFkdY0Wbs");
//      requestMap.put("UserCardCode", "3e44f223a1632abd");
        
//      cardEvent(requestMap, false);
        Message msg = new Message();
        msg.setAccessType("wechat");
        msg.setSender("111");
        msg.setReceiver("222");
        msg.setMessageType("text");
        msg.setContent("hello");
        String content = JsonUtil.toJson(msg);
        //UCS接受地址
        String url = SystemProperty.getInstance("config").getProperty("ucs.url")+"/sender/wechat/message";
        WechatHttpUtil.http(url, "POST",  content);
    }
}