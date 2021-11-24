package com.baeldung.jpa.uniqueconstraints;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "UniqueNumberAndStatus", columnNames = { "personNumber", "isActive" }),
                             @UniqueConstraint(name = "UniqueSecurityAndDepartment", columnNames = { "securityNumber", "departmentCode" }),
                             @UniqueConstraint(name = "UniqueNumberAndAddress", columnNames = { "personNumber", "address" }) })
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    
    private String name;
    
    private String password;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private Long personNumber;

    private Boolean isActive;
    
    private String securityNumber;
    
    private String departmentCode;

    @Column(unique = true)
    @JoinColumn(name = "addressId", referencedColumnName = "id")
    private Address address;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPersonNumber() {
        return personNumber;
    }

    public void setPersonNumber(Long personNumber) {
        this.personNumber = personNumber;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getScode() {
        return securityNumber;
    }

    public void setScode(String scode) {
        this.securityNumber = scode;
    }

    public String getDcode() {
        return departmentCode;
    }

    public void setDcode(String dcode) {
        this.departmentCode = dcode;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}