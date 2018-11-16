package com.baeldung.naming.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Account {

    @Id private Long id;

    private String defaultEmail;

    @OneToMany List<Preference> preferences;

    @Column(name = "\"Secondary_Email\"") private String secondaryEmail;
}
