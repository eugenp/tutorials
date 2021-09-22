package com.baeldung.architecture.clean.hexagonal.entitybased.user.application.adapter.external;

import com.baeldung.architecture.clean.hexagonal.entitybased.user.application.port.external.IDocumentValidatorService;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.port.external.DocumentIDEntity;
import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.port.external.IDocumentIdValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DocumentValidatorAdapter implements IDocumentIdValidator {
    private final IDocumentValidatorService documentValidatorService;

    @Override
    public DocumentIDEntity validateDocumentID(String documentID) {
        try {
            ResponseEntity<DocumentIDEntity> response = documentValidatorService.validate(documentID);
            return response.getStatusCode().is2xxSuccessful() && response.hasBody() ? response.getBody() : DocumentIDEntity.invalidDocumentID(documentID);
        } catch (Exception e) {
            return DocumentIDEntity.invalidDocumentID(documentID);
        }
    }
}
