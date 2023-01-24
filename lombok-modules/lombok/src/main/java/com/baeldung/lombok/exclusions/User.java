package com.baeldung.lombok.exclusions;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class User {

    @Setter(AccessLevel.NONE)
    private  long id;

    private String login;

    @Getter(AccessLevel.NONE)
    private int age;
}
