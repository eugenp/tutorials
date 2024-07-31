package com.baeldung.hibernate.arraymapping;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import org.hibernate.annotations.Type;

import com.vladmihalcea.hibernate.type.array.StringArrayType;

@Entity
public class User {

    @Id
    private Long id;

    private String name;

    @Column(columnDefinition = "text[]")
    @Type(value = com.baeldung.hibernate.arraymapping.CustomStringArrayType.class)
    private String[] roles;

    @Column(columnDefinition = "int[]")
    @Type(value = com.baeldung.hibernate.arraymapping.CustomIntegerArrayType.class)
    private Integer[] locations;

    @Type(StringArrayType.class)
    @Column(
        name = "phone_numbers",
        columnDefinition = "text[]"
        )
    private String[] phoneNumbers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public Integer[] getLocations() {
        return locations;
    }

    public void setLocations(Integer[] locations) {
        this.locations = locations;
    }

    public String[] getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String[] phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

}
