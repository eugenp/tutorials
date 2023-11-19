package com.baeldung.states;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntityWithCascade {
    @Id
    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private UserEntityWithCascade manager;

    public UserEntityWithCascade(String name) {
        this.name = name;
    }
}
