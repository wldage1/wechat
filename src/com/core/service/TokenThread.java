package com.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wechat.AccessTokenManager;
import com.wechat.message.AccessToken;
import com.wechat.util.SystemProperty;

/**
 * 
 * AccessToken 持久化
 * 
 */
public class TokenThread implements Runnable {
	private static Logger log = LoggerFactory.getLogger(TokenThread.class);

	public static AccessToken accessToken = null;  
	
	public void run() {
		while (true) {
			try {
				// 第三方用户唯一凭证  
				String appId = SystemProperty.getInstance("config").getProperty("Wechat.appID");
		        // 第三方用户唯一凭证密钥  
				String appSecret = SystemProperty.getInstance("config").getProperty("Wechat.appSecret");
		  
		        // 调用接口获取access_token  
				System.out.println("appId:" + appId + ",appSecret:" + appSecret);
				// http请求腾讯微信Token接口
				accessToken = AccessTokenManager.getAccessToken(appId, appSecret);

				if (accessToken != null) {
					// 将token值set到AccessToken.xml
					//AccessTokenManager.setToken(accessToken.getAccess_token(), accessToken.getExpires_in());
					
					log.info("==============    access_token    =======================");
					log.info("获取access_token成功，有效时长{}秒 token:{}", accessToken.getExpires_in(), accessToken.getAccess_token());
					log.info("==============    end    =======================");


					System.out.println("==============    access_token    =======================");
					System.out.println("获取access_token成功，有效时长"
							+ accessToken.getExpires_in() + "秒 token:"
							+ accessToken.getAccess_token());
					System.out.println("==============    end    =======================");

					// 休眠7000秒    
                    Thread.sleep(90 * 60 * 1000);

				} else {
					System.out.println("============== TokenThread    ===========");
					System.out.println("TokenThread  sleep 30 s");
					System.out.println("==============    end    =======================");
					// 如果access_token为null，30秒后再获取
					Thread.sleep(30 * 1000);
				}
			} catch (InterruptedException e) {
				try {
					System.out.println("============== TokenThread    ===========");
					System.out.println("TokenThread  sleep 30 s");
					System.out.println("==============    end    =======================");
					Thread.sleep(30 * 1000);
				} catch (InterruptedException e1) {
					log.error("{}", e1);
					e.printStackTrace();
				}
				log.error("{}", e);
				e.printStackTrace();
			}
		}
	}
	
}
