package com.baeldung.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
public class Contact {

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String name;
    @Email
    private String email;
    @Pattern(regexp = "\\d{3}-\\d{3}-\\d{4}")
    private String phone;

    public Contact() {
    }

    public Contact(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Contact {" +
                "id=" + id +
                ", name='" + name + "'" +
                ", email='" + email + "'" +
                ", phone='" + phone + "'" +
                " }";
    }
}
