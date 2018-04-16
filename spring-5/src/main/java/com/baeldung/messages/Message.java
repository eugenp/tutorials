package com.baeldung.messages;

public class Message {
	private int id;
	private String message;
	private String userName;
	private long sentAt;

	public Message(int id, String message, String userName, long sentAt) {
		this.id = id;
		this.message = message;
		this.userName = userName;
		this.sentAt = sentAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getSentAt() {
		return sentAt;
	}

	public void setSentAt(long sentAt) {
		this.sentAt = sentAt;
	}
}
