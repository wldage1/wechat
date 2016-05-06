package com.wechat.message.resp;

/**
 * 
 * 上传媒体文件用的消息对象（公众帐号 -> 普通用户）
 * 
 */
public class MediaMessage {
	private String type;// 媒体类型
	private String media_id;// 媒体id
	private String created_at;// 微信端上传成功时间
	private String errcode;// 状态码
	private String errmsg;// 状态消息

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

}
