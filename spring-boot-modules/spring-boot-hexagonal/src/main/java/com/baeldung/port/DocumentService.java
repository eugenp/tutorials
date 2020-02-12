package com.baeldung.port;

import com.ajay.documentservice.domain.Document;

public interface DocumentService {
	
	void createDocument(Document document);
	
	Document findById(String id);
	
}
