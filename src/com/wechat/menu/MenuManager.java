package com.wechat.menu;

import net.sf.json.JSONObject;

import com.wechat.AccessTokenManager;
import com.wechat.menu.entity.ButtonObject;
import com.wechat.menu.entity.Menu;
import com.wechat.message.AccessToken;
import com.wechat.util.SystemProperty;
import com.wechat.util.WechatHttpUtil;

/**
 * 微信菜单创建
 * 
 */
public class MenuManager {

	/**
	 * 
	 * 微信菜单创建
	 * 
	 * @param menu
	 * @param accessToken
	 * @return
	 */
	public static String createMenu(Menu menu, String accessToken) {

		String menuCreateUrl = SystemProperty.getInstance("config").getProperty("MenuCreateUrl");
		// 拼装创建菜单的url
		menuCreateUrl = menuCreateUrl.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		String jsonMenu = JSONObject.fromObject(menu).toString();
		// 调用接口创建菜单
		return WechatHttpUtil.httpRequest(menuCreateUrl, "POST", jsonMenu);
	}

	/**
	 * 
	 *  删除菜单-所有
	 *  @param accessToken
	 *  @return
	 */
	public static String deleteMenu(String accessToken) {
		String menuDeleteUrl = SystemProperty.getInstance("config").getProperty("MenuDeleteUrl");
		// 拼装删除菜单的url
		menuDeleteUrl = menuDeleteUrl.replace("ACCESS_TOKEN", accessToken);
		return WechatHttpUtil.httpRequest(menuDeleteUrl, "POST", null);
	}
	
	/**
	 * 
	 * 获取菜单列表数据
	 *  @param accessToken
	 *  @return
	 */
	public static String getMenu(String accessToken) {

		String menuListUrl = SystemProperty.getInstance("config").getProperty("MenuListUrl");
		// 拼装获取菜单数据的url
		menuListUrl = menuListUrl.replace("ACCESS_TOKEN", accessToken);
		// 调用接口获取菜单信息
		return WechatHttpUtil.httpRequest(menuListUrl, "POST", null);
	}

	public static void main(String[] args) {
		Menu menu = new Menu();
		ButtonObject bo1 = new ButtonObject();
		ButtonObject bo2 = new ButtonObject();
		ButtonObject bo3 = new ButtonObject();
		 
		bo1.setName("测试1");
		bo1.setType("view");
		bo1.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6868fd8dca43c3e2&redirect_uri=http%3A%2F%2Fcard.orientalwisdom.com%2Fudp%2Fcheckuser%2FregInfo&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");
		
		bo2.setName("测试2");
		bo2.setType("view");
		bo2.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6868fd8dca43c3e2&redirect_uri=http%3A%2F%2Fcard.orientalwisdom.com%2Fudp%2Fcheckuser%2FregInfo&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");
		
		bo3.setName("测试3");
		bo3.setType("view");
		bo3.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6868fd8dca43c3e2&redirect_uri=http%3A%2F%2Fcard.orientalwisdom.com%2Fudp%2Fcheckuser%2FregInfo&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect");
		menu.setButton(new ButtonObject[]{bo1,bo2,bo3});

		// 第三方用户唯一凭证  
		String appId = SystemProperty.getInstance("config").getProperty("Wechat.appID");
        // 第三方用户唯一凭证密钥  
		String appSecret = SystemProperty.getInstance("config").getProperty("Wechat.appSecret");
  
        // 调用接口获取access_token  
        AccessToken token = AccessTokenManager.getAccessToken(appId, appSecret);
		
//		deleteMenu(token.getAccess_token());
//		String str = createMenu(menu, token.getAccess_token());
		String str = getMenu(token.getAccess_token());
		System.out.println(str);
  
	}
}
