package com.baeldung.jpa.equality;

import javax.persistence.*;

@Entity
public class EqualByBusinessKey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    public EqualByBusinessKey() {
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

    @Override
    public int hashCode() {
        return java.util.Objects.hashCode(email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof EqualByBusinessKey) {
            if (((EqualByBusinessKey) obj).getEmail() == getEmail()) {
                return true;
            }
        }
        return false;
    }
}
