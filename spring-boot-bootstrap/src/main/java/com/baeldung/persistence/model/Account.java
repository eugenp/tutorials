package com.baeldung.persistence.model;
/*
 * created by pareshP on 02/04/19
 */

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String accountId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;
}
