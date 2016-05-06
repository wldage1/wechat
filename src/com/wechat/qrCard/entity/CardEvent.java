package com.wechat.qrCard.entity;

import java.io.Serializable;

public class CardEvent implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String oldOpenId;
	
	private String openId;
	
	private String cardId;
	
	private String cardCode;
	
	private String event;
	
	private String isGiveByFriend;

	public String getOldOpenId() {
		return oldOpenId;
	}

	public void setOldOpenId(String oldOpenId) {
		this.oldOpenId = oldOpenId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getIsGiveByFriend() {
		return isGiveByFriend;
	}

	public void setIsGiveByFriend(String isGiveByFriend) {
		this.isGiveByFriend = isGiveByFriend;
	}
	
}
