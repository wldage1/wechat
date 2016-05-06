package com.wechat.ucs;

import java.io.IOException;

import javax.servlet.ServletException;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wechat.AccessTokenManager;
import com.wechat.util.JsonUtil;
import com.wechat.util.WechatHttpUtil;

public class UcsWechatService {

    private static Logger log = LoggerFactory.getLogger(UcsWechatService.class);

    /**
     * 发送用户信息to UCS
     * @param user
     * @param url
     * @param fromUserName
     */
    public static String sendWechatUserInfoToUcs(String event, Message msg, String url, String fromUserName) {
        String result = "";
        try {
            String userInfo = UcsWechatService.getWechatUserInfo(fromUserName);
            JSONObject jsonObj = JSONObject.fromObject(userInfo); 
            
            if(!jsonObj.containsKey("errmsg")){
                jsonObj.put("wechatPublicId", msg.getReceiver());
                jsonObj.put("messageType", event);
                jsonObj.put("openId", fromUserName);
            }
            
            result = WechatHttpUtil.http(url, "POST",  jsonObj.toString());
            
        } catch (ServletException e) {
            log.error("----------  发送请求消息到UCS异常       ----------", e);
            return result;
        } catch (IOException e) {
            log.error("----------  发送请求消息到UCS异常       ----------", e);
            return result;
        }
        log.error("----------  发送客户消息到UCS <微信用戶信息>       ----------");
        log.info(JsonUtil.toJson(result));
        log.error("----------  发送客户消息到UCS <微信用戶信息>      ----------");
        
        return result;
    }

    /**
     * 取用户信息
     * @param fromUserName
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public static String getWechatUserInfo(String fromUserName) throws ServletException, IOException {
        String userStr = null;
        try {
            Message msgBean = new Message();
            msgBean.setReceiver(fromUserName);
            String token = AccessTokenManager.getToken();
            userStr = UcsReceive.getUserInfo(msgBean, token);
        } catch (Exception e) {
            log.error("----------  发送请求消息到微信接口，链接超时       ----------", e);
        }
        return userStr;
    }

    public static void main(String[] args) {
        JSONObject jsonOne = new JSONObject();
        JSONObject jsonTwo = new JSONObject();

        jsonOne.put("name", "kewen");
        jsonOne.put("age", "24");

        jsonTwo.put("nickname", "Dota");
        jsonTwo.put("hobby", "wow");

        JSONObject jsonThree = new JSONObject();

        jsonThree.putAll(jsonOne);
        jsonThree.putAll(jsonTwo);

        
        JSONObject jsonObj = JSONObject.fromObject("{\"subscribe\":1,\"openid\":\"oBJrQssqohwrKE20KJTHq7bgqN9w\",\"nickname\":\"王磊\",\"sex\":1,\"language\":\"zh_CN\",\"city\":\"沈阳\",\"province\":\"辽宁\",\"country\":\"中国\",\"headimgurl\":\"http:\\/\\/wx.qlogo.cn\\/mmopen\\/PiajxSqBRaEJQUQ3iaH3GicGJL8NyqGfpIkgaBtXSBWghdNKeR5JP5VZ5kl1Um3XvjKUqia6kaDCaN3hMUlV4wiaIhg\\/0\",\"subscribe_time\":1425949946,\"remark\":\"\"}"); 
        
        System.out.println("===========>"+jsonObj.get("openid").toString());

    }

}
