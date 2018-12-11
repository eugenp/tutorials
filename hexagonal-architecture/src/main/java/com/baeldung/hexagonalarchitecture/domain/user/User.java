package com.baeldung.hexagonalarchitecture.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * This class is domain object of user
 *
 * @author José Carlos Mazella Junior
 * @version 1.0 10/12/2018
 */
@Entity
@Table(name = "USER", schema = "USR")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonProperty("id")
    private Long id;

    @Getter
    @NotNull
    @Column(name = "NAME")
    @JsonProperty("name")
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    @JsonProperty("status")
    private Status status;

    @NotNull
    @Column(name = "EMAIL")
    @JsonProperty("email")
    private String email;

    public User(final String name, final String email) {
        this.name = name;
        this.status = Status.ACTIVE;
        this.email = email;
    }

    private User() {
        this(null, null);
    }

    public boolean canBeDeleted() {
        return status.equals(Status.INACTIVE);
    }

}
