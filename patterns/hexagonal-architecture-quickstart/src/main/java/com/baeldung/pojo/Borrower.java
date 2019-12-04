package com.baeldung.pojo;

import lombok.Data;

@Data
public class Borrower {

    private String uid;

    private String name;

    public Borrower(String uid, String name) {
        super();
        this.uid = uid;
        this.name = name;
    }

}
