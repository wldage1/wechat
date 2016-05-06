package com.wechat.message.req;

 

/**
 * 
 * 图片消息对象
 * 
 */
public class ImageMessage extends BaseMessage {

	private Image image;// 图片对象

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}