package org.baeldung.persistence.domain.model;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiRelation;
import io.katharsis.resource.annotations.JsonApiResource;

// TODO: Auto-generated Javadoc
/**
 * The Class Role.
 */
@JsonApiResource(type = "roles")
public class Role {

    /** The id. */
    @JsonApiId
    private Long id;

    /** The name. */
    private String name;

    /** The user. */
    @JsonApiRelation(opposite = "roles")
    private User user;

    /**
     * Instantiates a new role.
     */
    public Role() {
        super();
    }

    /**
     * Instantiates a new role.
     *
     * @param id the id
     * @param name the name
     */
    public Role(Long id, String name) {
        super();
        this.id = id;
        this.name = name;
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
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the user.
     *
     * @param user the new user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "article[id=" + id + ", name=" + name + "]";
    }
}
