package com.baeldung.port;

import com.baeldung.domain.Document;

public interface DocumentService {
	
	void createDocument(Document document);
	
	Document findById(String id);
	
}
