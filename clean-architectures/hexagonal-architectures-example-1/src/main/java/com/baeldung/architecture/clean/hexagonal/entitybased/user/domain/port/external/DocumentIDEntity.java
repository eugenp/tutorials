package com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.port.external;

import lombok.Getter;

@Getter
public class DocumentIDEntity {
    private static final String EMPTY_STR = "";
    private boolean valid;
    private String name;
    private String surname;
    private String documentID;

    public DocumentIDEntity(boolean valid, String name, String surname, String documentID) {
        this.valid = valid;
        this.name = name;
        this.surname = surname;
        this.documentID = documentID;
    }

    public static DocumentIDEntity invalidDocumentID(String documentID) {
        return new DocumentIDEntity(false, EMPTY_STR, EMPTY_STR, documentID);
    }
}
