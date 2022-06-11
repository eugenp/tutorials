package com.baeldung.apikeysecretauth.repository.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_keys")
public class UserKeysData {
    @Id
    private String apiKey;
    private String apiSecret;
    @ElementCollection
    private Set<String> permissions = new HashSet<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "app_user_id")
    private UserData appUser;
}
