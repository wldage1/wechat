package com.wechat.message.req;

/**
 * 文本消息对象
 * 
 */
public class TextMessage extends BaseMessage {
	// 消息内容
	private Text text;// 文本消息对象

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

}