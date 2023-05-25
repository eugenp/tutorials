package com.baeldung.tapestry.pages;

import org.apache.tapestry5.alerts.AlertManager;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

public class Login {
    @Inject
    private Logger logger;

    @Inject
    private AlertManager alertManager;

    @InjectComponent
    private Form login;

    @Property
    private String email;

    @Property
    private String password;

    void onValidateFromLogin() {
        if(email == null || password == null) {
            alertManager.error("Email/Password is null");
            login.recordError("Validation failed");
        }
    }

    Object onSuccessFromLogin() {
        alertManager.success("Welcome! Login Successful");
        return Home.class;
    }

    void onFailureFromLogin() {
        alertManager.error("Please try again with correct credentials");
    }

}
