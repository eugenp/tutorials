package com.baeldung.beaninjectiontypes.constructorbased;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.beaninjectiontypes.services.MessageService;

@Component("constructorbased-service")
public class UserService {

	private MessageService service;

	@Autowired
	public UserService(MessageService svc) {
		this.service = svc;
	}

	public boolean processMessage(String message, String sender, String reciever) {
		return this.service.sendMessage(message, sender, reciever);
	}
}
