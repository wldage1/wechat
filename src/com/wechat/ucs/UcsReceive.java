package com.wechat.ucs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wechat.AccessTokenManager;
import com.wechat.message.AccessToken;
import com.wechat.message.WechatMsg;
import com.wechat.message.req.Image;
import com.wechat.message.req.ImageMessage;
import com.wechat.message.req.Text;
import com.wechat.message.req.TextMessage;
import com.wechat.message.resp.MediaMessage;
import com.wechat.util.ConfigManager;
import com.wechat.util.JsonUtil;
import com.wechat.util.WechatHttpUtil;

/**
 * 微信服务下行接口,接收请求信息后，发送微信消息
 */
public class UcsReceive {

    private static Logger log = LoggerFactory.getLogger(UcsReceive.class);

    /**
     * 发送微信下行消息
     * @param msgBean
     * @return
     * @throws Exception
     */
    public static WechatMsg sendMsg(String openId, String message) throws Exception {
	    
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

		System.out
				.println("===========  腾讯返回状态   <文本消息>  ================");
		System.out.println(JsonUtil.toJson(wechatMsg));
		System.out.println("===========  end  ================");

		return wechatMsg;
	}

    /**
     * 发送微信下行消息
     * @param msgBean
     * @return
     */
    public static WechatMsg sendMsg(Message msgBean) throws Exception {
        WechatMsg wechatMsg = null;
        
        // 消息类型
        String type = msgBean.getMessageType();

        String token = AccessTokenManager.getToken();

        if (type.equals("text")) {

            TextMessage t = new TextMessage();
            t.setMsgtype(type);
            Text ts = new Text();
            ts.setContent(msgBean.getContent());
            t.setText(ts);
            t.setTouser(msgBean.getReceiver());

            // 响应状态
            wechatMsg = WechatHttpUtil.sendTextMessage(t, token);

            log.info("===========  腾讯返回状态   <文本消息>  ================");
            log.info(JsonUtil.toJson(wechatMsg));
            log.info("===========  end  ================");

            System.out.println("===========  腾讯返回状态   <文本消息>  ================");
            System.out.println(JsonUtil.toJson(wechatMsg));
            System.out.println("===========  end  ================");

        } else if (type.equals("image")) {

            MediaMessage mediaMsg = WechatHttpUtil.uploadMedia(token, "image", msgBean.getPicUrl());

            // 当图片上传成功，发送图片消息到微信接口
            if (mediaMsg.getMedia_id() != null) {
                Image image = new Image();
                ImageMessage imMessage = new ImageMessage();
                image.setMedia_id(mediaMsg.getMedia_id());
                imMessage.setImage(image);
                imMessage.setMsgtype("image");
                imMessage.setTouser(msgBean.getReceiver());

                // 响应状态
                wechatMsg = WechatHttpUtil.sendImgMessage(imMessage, token);

                log.info("===========  腾讯返回状态   <图片消息>  ================");
                log.info(JsonUtil.toJson(wechatMsg));
                log.info("===========  end  ================");

                System.out.println("===========  腾讯返回状态   <图片消息>  ================");
                System.out.println(JsonUtil.toJson(wechatMsg));
                System.out.println("===========  end  ================");

            } else {
                wechatMsg = new WechatMsg();
                // 图片上传失败，返回错误消息给 UCS
                wechatMsg.setErrcode(mediaMsg.getErrcode());
                wechatMsg.setErrmsg(mediaMsg.getErrmsg());

                log.info("===========  腾讯返回状态   <图片消息>  ================");
                log.info(JsonUtil.toJson(wechatMsg));
                log.info("===========  end  ================");

                System.out.println("===========  腾讯返回状态   <图片消息>  ================");
                System.out.println(JsonUtil.toJson(wechatMsg));
                System.out.println("===========  end  ================");
            }
        }
        
        return wechatMsg;
    }

    /**
     * 获取粉丝信息
     * @param msgBean
     * @param accessToken 
     * @return
     * @throws IOException 
     */
    public static String getUserInfo(Message msgBean, String token) throws IOException {
        String result = "";
        if(token != null ){
            // 拼装url=https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
            String httpUrl = ConfigManager.getValue("wechatCustomer").replace("ACCESS_TOKEN", token).replace("OPENID", msgBean.getReceiver());
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("POST", "/violations HTTP/1.1");
            connection.setRequestProperty("ContentType","text/xml;charset=utf-8"); 
            connection.setRequestMethod("POST");
            connection.connect();
            
            StringBuffer sb = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            result = sb.toString();
            
            log.info("===========  腾讯返回状态   <微信用戶信息>  ================");
            log.info(JsonUtil.toJson(result));
            log.info("===========  end  ================");
        }
        return result;
    }
    
    public static void main(String[] args) throws IOException {
        
        String appId = "wxad0fda1d0adf5278";
        String appSecret = "647cda7843e1ef5d4137abb5f28180a7";
        AccessToken accessToken = AccessTokenManager.getAccessToken(appId, appSecret);
        Message msg = new Message();
        msg.setReceiver("oBJrQssqohwrKE20KJTHq7bgqN9w");
        msg.setContent("/:P-(/:,@x");
      
        TextMessage t = new TextMessage();
        t.setMsgtype("text");
        Text ts = new Text();
        ts.setContent("/:P-(/:,@x");
        t.setText(ts);
        t.setTouser("oBJrQssqohwrKE20KJTHq7bgqN9w");

        // 响应状态
         WechatHttpUtil.sendTextMessage(t, accessToken.getAccess_token());

//        String userJson = getUserInfo(msg, accessToken.getAccess_token());
        //userJson = JsonUtil.toJson(userJson);
//        JSONObject jsonObj = JSONObject.fromObject(userJson);
//        if(jsonObj.containsKey("errmsg")){
//            System.out.println("error :"+jsonObj);
//        }else{
//            System.out.println("OK ====> "+userJson);
//        }
    }
}
