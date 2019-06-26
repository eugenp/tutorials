package com.baeldung.hexagonalArchitecture.serviceAdapter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.hexagonalArchitecture.entity.Message;
import com.baeldung.hexagonalArchitecture.repository.MessageRepositoryPort;

@Service
public class MessageServiceAdapter implements MessageRepositoryPort {

	 @PersistenceContext
	 private EntityManager entityManager;
	
	 @Transactional
	 @Override
	 public void addMessage(Message request) {
		
		Message msg = new Message();
		msg.setMessage(request.getMessage());
		msg.setMessage_type(request.getMessage_type());
	   
		entityManager.persist(msg);
	}

}
