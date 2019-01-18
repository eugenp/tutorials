package com.baeldung.mediator.login.contracts;

import com.baeldung.mediator.MediatorResponse;

public class LoginResponse implements MediatorResponse {

    private String name;

    public LoginResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
