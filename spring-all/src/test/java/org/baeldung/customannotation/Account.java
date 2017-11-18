package org.baeldung.customannotation;

import java.io.Serializable;

public class Account implements Serializable {

    private static final long serialVersionUID = 7857541629844398625L;

    private Long id;
    private String email;
    private Person person;

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
