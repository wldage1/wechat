package com.wechat.menu.entity;

import java.io.Serializable;

public class ButtonObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6567993149688466511L;

	private String type;
	private String name;
	private String url;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
