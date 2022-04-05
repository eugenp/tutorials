package com.baeldung.hibernate.arraymapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import com.vladmihalcea.hibernate.type.array.StringArrayType;

import org.hibernate.annotations.*;

@TypeDefs({
    @TypeDef(
        name = "string-array",
        typeClass = StringArrayType.class
        )
})
@Entity
public class User {

    @Id
    private Long id;

    private String name;

    @Column(columnDefinition = "text[]")
    @Type(type = "com.baeldung.hibernate.arraymapping.CustomStringArrayType")
    private String[] roles;

    @Column(columnDefinition = "int[]")
    @Type(type = "com.baeldung.hibernate.arraymapping.CustomIntegerArrayType")
    private Integer[] locations;

    @Type(type = "string-array")
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
