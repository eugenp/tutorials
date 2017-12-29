package com.baeldung.beaninjectiontypes.setterbased;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.beaninjectiontypes.services.MessageService;

@Component("setterbased-service")
public class UserService {

	private MessageService service;

	@Autowired
	public void setService(MessageService svc) {
		this.service = svc;
	}

	public boolean processMessage(String message, String sender, String reciever) {
		return this.service.sendMessage(message, sender, reciever);
	}
}
