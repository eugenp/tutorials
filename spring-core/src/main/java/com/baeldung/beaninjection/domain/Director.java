package com.baeldung.beaninjection.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Director {

    private PersonalData personalData;

    @Autowired
    public Director(PersonalData personalData) {
        this.personalData = personalData;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    @Override
    public String toString() {
        return "Director{" + personalData + "}";
    }
    
}
