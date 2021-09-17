package com.baeldung.architecture.clean.hexagonal.entitybased.user.application.port.external;

import com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.port.external.DocumentIDEntity;
import org.springframework.http.ResponseEntity;

public interface IDocumentValidatorService {
    ResponseEntity<DocumentIDEntity> validate(String documentID);
}
