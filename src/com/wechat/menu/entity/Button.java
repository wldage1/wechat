package com.wechat.menu.entity;

/**
 * 
 * 菜单按钮
 * 
 */
public class Button {
	private String name;// 菜单标题，不超过16个字节，子菜单不超过40个字节
	private SubButton[] sub_button;// 二级菜单

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SubButton[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(SubButton[] sub_button) {
		this.sub_button = sub_button;
	}

}