package com.baeldung.hexagonal;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
public class User {
    @Id
    private UUID id;
    private String name;
    private String surname;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    //constructor and getters
}
