package com.wechat.qrCard.entity;

import java.io.Serializable;

public class Card implements Serializable{
	private static final long serialVersionUID = 1015884349543874066L;
	
	private String card_type;
	private GeneralCoupon general_coupon;
	
	public String getCard_type() {
		return card_type;
	}
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	public GeneralCoupon getGeneral_coupon() {
		return general_coupon;
	}
	public void setGeneral_coupon(GeneralCoupon general_coupon) {
		this.general_coupon = general_coupon;
	}
	
	
}
