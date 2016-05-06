package com.wechat.qrCard.entity;

import java.io.Serializable;

public class Sku implements Serializable{

	private static final long serialVersionUID = -1239754971305954806L;
	
	private int quantity;//上架的数量。（不支持填写 0 或无限大）

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	

}
