package com.wechat.qrCard.entity;

import java.io.Serializable;

public class Ticket implements Serializable{
	
	private static final long serialVersionUID = 2470659076262440365L;
	
	private int  expires_in ;
	private int   errcode;
	private String   errmsg;
	private String   ticket;
	
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
	public int getErrcode() {
		return errcode;
	}
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	
	
}
