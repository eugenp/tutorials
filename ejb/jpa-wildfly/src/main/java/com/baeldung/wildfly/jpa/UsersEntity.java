package com.baeldung.wildfly.jpa;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "public", catalog = "postgresdb")
public class UsersEntity {
    private String username;
    private String email;
    private Integer postalNumber;

    @Id
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "postal_number")
    public Integer getPostalNumber() {
        return postalNumber;
    }

    public void setPostalNumber(Integer postalNumber) {
        this.postalNumber = postalNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (postalNumber != null ? !postalNumber.equals(that.postalNumber) : that.postalNumber != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (postalNumber != null ? postalNumber.hashCode() : 0);
        return result;
    }
}
