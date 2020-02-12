package com.ajay.documentservice.port.impl;

import com.ajay.documentservice.domain.Document;
import com.ajay.documentservice.port.DocumentRepo;
import com.ajay.documentservice.port.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentServiceImpl implements DocumentService {
	
	@Autowired
	private DocumentRepo documentRepo;
	
	@Override
	public void createDocument(Document document) {
		documentRepo.storeDocument(document);
	}
	
	@Override
	public Document findById(String id) {
		return documentRepo.findDocumentById(id);
	}
}
