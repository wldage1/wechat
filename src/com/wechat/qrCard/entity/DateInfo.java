package com.wechat.qrCard.entity;

import java.io.Serializable;

public class DateInfo implements Serializable{
	private static final long serialVersionUID = 7117958551954872603L;
	
	private int type ;
	private long begin_timestamp;
	private long end_timestamp;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public long getBegin_timestamp() {
		return begin_timestamp;
	}

	public void setBegin_timestamp(long begin_timestamp) {
		this.begin_timestamp = begin_timestamp;
	}

	public long getEnd_timestamp() {
		return end_timestamp;
	}

	public void setEnd_timestamp(long end_timestamp) {
		this.end_timestamp = end_timestamp;
	}
	
}
