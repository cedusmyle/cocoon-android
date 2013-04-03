package com.bacapps.cocoon.models;

public class Gift {
	private String senderId, assetId, sentAt, tapCount, wrap;
	
	public Gift(String senderId, String assetId, String sentAt, String tapCount, String wrap) {
		super();
		this.senderId = senderId;
		this.assetId = assetId;
		this.sentAt = sentAt;
		this.tapCount = tapCount;
		this.wrap = wrap;
	}
	
	public String getSenderId() {
		return senderId;
	}
	
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	
	public String getAssetId() {
		return assetId;
	}
	
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	
	public String getSentAt() {
		return sentAt;
	}
	
	public void setSentAt(String sentAt) {
		this.sentAt = sentAt;
	}
	
	public String getTapCount() {
		return tapCount;
	}
	
	public void setTapCount(String tapCount) {
		this.tapCount = tapCount;
	}
	
	public String getWrap() {
		return wrap;
	}
	
	public void setWrap(String wrap) {
		this.wrap = wrap;
	}
	
}
