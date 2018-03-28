package com.baeldung.methodinjections;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("schoolNotification")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SchoolNotification {

    private String message = "SCHOOL_OPEN";

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
