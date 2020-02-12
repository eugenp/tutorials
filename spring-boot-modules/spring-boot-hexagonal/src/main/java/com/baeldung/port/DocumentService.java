package com.ajay.documentservice.port;

import com.ajay.documentservice.domain.Document;

public interface DocumentService {
	
	void createDocument(Document document);
	
	Document findById(String id);
	
}
