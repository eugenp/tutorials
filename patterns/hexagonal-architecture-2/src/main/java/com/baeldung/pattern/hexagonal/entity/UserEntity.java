package com.baeldung.pattern.hexagonal.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Immutable
@Entity(name = "USER")
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String login;
    private String name;
    private String surname;


}



