package com.wechat.qrCard;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.wechat.util.Contact;
import com.wechat.util.HttpUtil;

import net.sf.json.JSONObject;

public class Qrcode {
	
	public static Qrcode newInstance(){
		return new Qrcode();
	}
	
	private static String addParameter() {
		Map<String, String> sceneMap = new HashMap<String, String>();
		sceneMap.put("scene_id", "123");
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("action_name", "QR_LIMIT_SCENE");
		map.put("action_info", JSONObject.fromObject(sceneMap));
		
		JSONObject jsonObject = JSONObject.fromObject(map);
		System.out.println(jsonObject);
		
		return jsonObject.toString();
	}
	
	public static void main(String[] args) {
		
		try {
			String token = HttpUtil.getAccessTokenJson();
			System.out.println(token);
			
			String temp_ticket_url = String.format(Contact.TICKET_URL, token);
			
			String ticket = HttpUtil.httpsPost(temp_ticket_url, addParameter());
			System.out.println(ticket);
			
			JSONObject ticketJson = JSONObject.fromObject(ticket);
			if(!"40013".equals(ticketJson.get("errcode"))){
				String qr_url = String.format(Contact.QRCODE_URL, ticketJson.get("ticket"));
				String relsut = HttpUtil.httpsGet(qr_url);
				System.out.println(relsut);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
