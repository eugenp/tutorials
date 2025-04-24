package com.baeldung.tutorials.passkey.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "PASSKEY_USERS")
public class PasskeyUser {
    @Id
    @Column(value = "ID")
    public Long id;

    @Column(value = "EXTERNAL_ID")
    public String externalId;

    @Column(value = "NAME")
    public String name;

    @Column(value = "DISPLAY_NAME")
    public String displayName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}