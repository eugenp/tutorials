package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name = "users")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String username;

    private String email;

    @Column(name = "postal_number")
    private Integer postalNumber;

    public User() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPostalNumber() {
        return this.postalNumber;
    }

    public void setPostalNumber(Integer postalNumber) {
        this.postalNumber = postalNumber;
    }

}