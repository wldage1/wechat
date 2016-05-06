package com.wechat.message;

/**
 * 
 * 腾信微信接口返回消息对象. </br> http://mp.weixin.qq.com/wiki/index.php?title=全局返回码说明</br>
 * 
 */
public class WechatMsg {
	private String errcode;// 状态码
	private String errmsg;// 状态值

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
