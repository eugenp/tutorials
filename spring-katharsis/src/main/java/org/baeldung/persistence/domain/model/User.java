package org.baeldung.persistence.domain.model;

import java.util.ArrayList;
import java.util.List;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiRelation;
import io.katharsis.resource.annotations.JsonApiResource;

/**
 * @author krishan.gandhi
 * The Class User.
 */
@JsonApiResource(type = "users")
public class User {

    /** The id. */
    @JsonApiId
    private Long id;

    /** The username. */
    private String username;

    /** The email. */
    private String email;

    /** The roles. */
    @JsonApiRelation(opposite = "user")
    private List<Role> roles = new ArrayList<>();

    /**
     * Instantiates a new user.
     */
    public User() {
        super();
    }

    /**
     * Instantiates a new user.
     *
     * @param id the id
     * @param username the username
     * @param email the email
     */
    public User(Long id, String username, String email) {
        super();
        this.id = id;
        this.username = username;
        this.email = email;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username            the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the roles.
     *
     * @return the roles
     */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * Sets the roles.
     *
     * @param roles            the roles to set
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "person[id=" + id + ", name=" + username + ", email=" + email + "]";
    }
}
