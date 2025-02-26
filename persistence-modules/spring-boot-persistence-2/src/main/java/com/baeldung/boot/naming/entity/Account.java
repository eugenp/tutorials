package com.baeldung.boot.naming.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Account {

    @Id private Long id;

    private String defaultEmail;

    @OneToMany List<Preference> preferences;

    @Column(name = "\"Secondary_Email\"") private String secondaryEmail;
}
