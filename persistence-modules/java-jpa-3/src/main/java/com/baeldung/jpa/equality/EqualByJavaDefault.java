package com.baeldung.jpa.equality;

import jakarta.persistence.*;

@Entity
public class EqualByJavaDefault implements Cloneable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    public EqualByJavaDefault() {
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

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
