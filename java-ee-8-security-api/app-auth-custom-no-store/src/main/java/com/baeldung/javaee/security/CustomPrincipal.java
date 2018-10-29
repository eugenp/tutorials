package com.baeldung.javaee.security;

import java.security.Principal;

public class CustomPrincipal implements Principal {

    private UserDetail userDetail;

    public CustomPrincipal(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    @Override
    public String getName() {
        return userDetail.getLogin();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ":" + getName();
    }
}
