package com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.port.external;

public interface IDocumentIdValidator {
    DocumentIDEntity validateDocumentID(String documentID);
}
