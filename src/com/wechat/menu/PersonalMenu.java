package com.wechat.menu;

import java.io.IOException;
import java.net.URLDecoder;

import com.wechat.util.Contact;
import com.wechat.util.HttpUtil;
import com.wechat.util.ReadFileIO;

/**
 * 微信自定义菜单
 *   个性化菜单需要在自定菜单创建完成后，才能添加
 * @author wang.l
 * @date 2016年4月20日 下午5:55:32
 */
public class PersonalMenu {
	
	/**
	 * 创建自定义菜单
	 * @author wang.l
	 * @date 2016年4月20日
	 * @return String
	 */
	public String createMenu(String accessToken, String menuStr) throws IOException{
		String requestURL=String.format(Contact.MENU_CREATE_URL, accessToken);
		String createMenuResultJson=HttpUtil.httpsPost(requestURL, menuStr);
		return createMenuResultJson;
	}
	
	/**
	 * 查询自定义菜单
	 * @author wang.l
	 * @date 2016年4月20日
	 * @return String
	 */
	public String getMenuJson(String accessToken) throws IOException{
		String requestURL=String.format(Contact.MENU_GET_URL, accessToken);
		String menuJson=HttpUtil.httpsGet(requestURL);
		return menuJson;
	}
	
	/**
	 * 创建个性化菜单
	 * @author wang.l
	 * @date 2016年4月20日
	 * @return String
	 */
	public String createConditionalMenu(String accessToken, String menuStr) throws IOException{
		String requestURL=String.format(Contact.MENU_CREATE_CONDITIONAL_URL, accessToken);
		String createMenuResultJson=HttpUtil.httpsPost(requestURL, menuStr);
		return createMenuResultJson;
	}
	
	@SuppressWarnings({ "static-access", "deprecation" })
	public static void main(String[] args) {
		try {
			PersonalMenu ds = new PersonalMenu();
			String token = HttpUtil.getAccessTokenJson();
			System.out.println(token);
			ReadFileIO fo = new ReadFileIO();
			
			String menu1 = URLDecoder.decode(PersonalMenu.class.getClassLoader().getResource("com/wechat/menu/menu.txt").getFile());
	        String str1 = fo.read(menu1);
			String menuJson1 = ReadFileIO.getMenuJson(str1);
			String result1 = ds.createMenu(token, menuJson1);//創建自定義菜單
			System.out.println(result1);
			
			String menu2 = URLDecoder.decode(PersonalMenu.class.getClassLoader().getResource("com/wechat/menu/menu2.txt").getFile());
		    String str2 = fo.read(menu2);
			String menuJson2 = ReadFileIO.getMenuJson(str2);
			String result2 = ds.createConditionalMenu(token, menuJson2);//創建個性化菜單
			
			System.out.println(result2);
//			String menu = ds.delMenu(token);
			String menu = ds.getMenuJson(token);
			System.out.println(menu);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
