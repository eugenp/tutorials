package com.baeldung.beaninjectiontypes.constructorbased;

import com.baeldung.beaninjectiontypes.services.MessageService;

public class UserServiceXML {

	private MessageService service;

	public UserServiceXML(MessageService svc) {
		this.service = svc;
	}

	public boolean processMessage(String message, String sender, String reciever) {
		return this.service.sendMessage(message, sender, reciever);
	}
}
