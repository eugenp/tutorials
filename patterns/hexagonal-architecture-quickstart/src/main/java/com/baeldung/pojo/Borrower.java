package com.baeldung.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Borrower {

    @Id
    private String uid;

    private String firstName;

    private String lastName;

}
