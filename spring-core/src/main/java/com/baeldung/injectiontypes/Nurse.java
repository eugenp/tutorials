package com.baeldung.injectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Nurse {
    private Hospital hospital;

    public Nurse() {
        //TODO
    }

    public Hospital getHospital() {
        return hospital;
    }

    @Autowired
    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }
}