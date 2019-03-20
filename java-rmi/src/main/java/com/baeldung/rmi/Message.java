package com.baeldung.rmi;

import java.io.Serializable;

public class Message implements Serializable {
	
	private String messageText;
	
	private String contentType;
	
	public Message() {
	}
	
	public Message(String messageText, String contentType) {
		
		this.messageText = messageText;
		this.contentType = contentType;
	}
	
	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
}
