package com.wechat.message.req;

/**
 * 
 * 消息基类（公众帐号 <- 普通用户）
 * 
 */
public class BaseMessage {

	private String touser;// 发送方帐号（一个OpenID）

	private String msgtype;// 消息类型（text/image/location/link）

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

}