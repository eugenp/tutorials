package com.baeldung.hexagonalArchitecture.repository;

import com.baeldung.hexagonalArchitecture.entity.Message;

public  interface MessageRepositoryPort {
	
	void addMessage(Message request);
}
