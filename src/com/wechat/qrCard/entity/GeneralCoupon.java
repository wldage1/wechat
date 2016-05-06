package com.wechat.qrCard.entity;

import java.io.Serializable;

public class GeneralCoupon implements Serializable{
	
	private static final long serialVersionUID = -4507968904007890663L;
	private BaseInfo base_info;
	private String default_detail;
	public BaseInfo getBase_info() {
		return base_info;
	}
	public void setBase_info(BaseInfo base_info) {
		this.base_info = base_info;
	}
	public String getDefault_detail() {
		return default_detail;
	}
	public void setDefault_detail(String default_detail) {
		this.default_detail = default_detail;
	}
	
}
