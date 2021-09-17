package com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.port.external;

public interface IDocumentIdValidatorAdapter {
    DocumentIDEntity validateDocumentID(String documentID);
}
