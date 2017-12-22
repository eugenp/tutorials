package com.baeldung.beaninjectiontypes.setter;

public class Hospital {

    private Patient patient;

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getPatientName() {
        return patient.getFirstName();
    }
}
