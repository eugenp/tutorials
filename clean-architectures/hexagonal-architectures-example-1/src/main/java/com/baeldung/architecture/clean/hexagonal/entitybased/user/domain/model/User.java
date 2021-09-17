package com.baeldung.architecture.clean.hexagonal.entitybased.user.domain.model;

import com.baeldung.architecture.clean.hexagonal.entitybased.shared.domain.model.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {
    private String name;
    private String surname;
    private String documentID;

    private User(Long id) {
        super(id, LocalDateTime.now());
        name = null;
        surname = null;
        documentID = null;
    }

    private User(Long id, String name, String surname, String documentID) {
        super(id, LocalDateTime.now());
        this.name = name;
        this.surname = surname;
        this.documentID = documentID;
    }

    public static User of(Long id) {
        return new User(id);
    }

    public static User of(Long id, User user) {
        return new User(id, user.getName(), user.getSurname(), user.getDocumentID());
    }
}
