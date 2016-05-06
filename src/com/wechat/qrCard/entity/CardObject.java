package com.wechat.qrCard.entity;

import java.io.Serializable;

public class CardObject implements Serializable{

	private static final long serialVersionUID = 1931192539471939948L;
	private Card card;
	
	public Card getCard() {
		return card;
	}
	public void setCard(Card card) {
		this.card = card;
	}
	
	
	
}
