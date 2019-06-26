package com.baeldung.hexagonalArchitecture.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonalArchitecture.entity.Message;
import com.baeldung.hexagonalArchitecture.repository.MessageRepositoryPort;

@Service
public class MessageService {

	@Autowired
	private MessageRepositoryPort messageRepositoryPort;
	
	public void addMessage(Message request) {
		messageRepositoryPort.addMessage(request);
	}
	
}
