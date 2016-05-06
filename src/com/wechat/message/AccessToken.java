package com.wechat.message;

/**
 * 
 * 腾讯AccessToken消息对象</Br> 具体请参照
 * http://mp.weixin.qq.com/wiki/index.php?title=获取access_token
 * 
 */
public class AccessToken {

	private String access_token;// 获取到的凭证

	private String expires_in;// 凭证有效时间，单位：秒

	private String errcode;// 状态码

	private String errmsg;// 状态值

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}

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
}