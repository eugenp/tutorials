package com.baeldung.adapter;

import com.ajay.documentservice.domain.Document;
import com.ajay.documentservice.port.DocumentRepo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class DocumentRepositoryImpl implements DocumentRepo {
	
	private Map<String, Document> documentMap = new HashMap<>();
	
	@Override
	public void storeDocument(Document document) {
		documentMap.put(document.getId(), document);
	}
	
	@Override
	public Document findDocumentById(String id) {
		return documentMap.get(id);
	}
}
