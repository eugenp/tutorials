package com.baeldung.dependencyinjections;

public class MailBox {


	private Message message;

	public MailBox(Message message) {
		this.message = message;
	}
	public MailBox(String message) {
		Message msg=new Message();
		msg.setContent(message);
		this.message=msg;
	}

	public String getMessage() {
		return message.getContent();
	}
}
