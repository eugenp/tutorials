package com.baeldung.pattern.cleanarchitecture.usercreation;

interface User {
    boolean passwordIsValid();

    String getName();

    String getPassword();
}
