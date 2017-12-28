package com.baeldung.beaninjectiontypes.setterbased;

import com.baeldung.beaninjectiontypes.services.MessageService;

public class UserServiceXML {

	private MessageService service;

	public void setService(MessageService svc) {
		this.service = svc;
	}

	public boolean processMessage(String message, String sender, String reciever) {
		return this.service.sendMessage(message, sender, reciever);
	}
}
