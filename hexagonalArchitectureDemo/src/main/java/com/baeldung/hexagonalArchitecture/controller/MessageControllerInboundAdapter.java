package com.baeldung.hexagonalArchitecture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonalArchitecture.entity.Message;
import com.baeldung.hexagonalArchitecture.inbound.MessageInboundPort;
import com.baeldung.hexagonalArchitecture.service.MessageService;
import com.baeldung.hexagonalArchitecture.serviceAdapter.MessageServiceAdapter;

@RequestMapping("/inboundMessage")
@RestController
public class MessageControllerInboundAdapter implements MessageInboundPort{

	@Autowired
	private MessageService messageService;
	
	@Override
	@PostMapping(value = "/addMessage", consumes = {"application/json"})
	public void addMessage(@RequestBody Message request) {
	 messageService.addMessage(request);
	   
			  
	}

}
