package com.wechat.message.resp;

/**
 * 
 * 文本消息（公众帐号 -> 普通用户）
 * 
 */
public class TextMessage extends BaseMessage {
	// 回复的消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}