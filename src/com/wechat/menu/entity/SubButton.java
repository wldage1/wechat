package com.wechat.menu.entity;

/**
 * 
 * 二级菜单
 * 
 */
public class SubButton {
	private String type;// 菜单的响应动作类型，目前有click、view两种类型
						// click 被动响应，view 相当于访问业务系统页面且为html5页面
	private String name;// 菜单标题，不超过16个字节，子菜单不超过40个字节
	private String key;// 菜单KEY值，用于消息接口推送，不超过128字节
	private SubButton[] sub_button;// 二级菜单数组，个数应为1~5个

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public SubButton[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(SubButton[] sub_button) {
		this.sub_button = sub_button;
	}

}
