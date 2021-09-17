package com.baeldung.architecture.clean.hexagonal.entitybased.user.infrastructure.external;

import com.baeldung.architecture.clean.hexagonal.entitybased.user.application.port.external.IDocumentValidatorService;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.port.external.DocumentIDEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DocumentIdValidatorServiceImpl implements IDocumentValidatorService {
    private final String documentIdURL;
    private final RestTemplate restTemplate;

    @Override
    public ResponseEntity<DocumentIDEntity> validate(String documentID) {
        return restTemplate.getForEntity(documentIdURL + "/" + documentID, DocumentIDEntity.class);
    }

    public DocumentIdValidatorServiceImpl(@Value("user.infra.external.documentvalidator.url") String documentIdURL, RestTemplate restTemplate) {
        this.documentIdURL = documentIdURL;
        this.restTemplate = restTemplate;
    }
}
