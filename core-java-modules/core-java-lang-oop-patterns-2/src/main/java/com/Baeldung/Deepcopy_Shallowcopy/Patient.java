package com.Baeldung.Deepcopy_Shallowcopy;

import java.io.Serializable;

public class Patient implements Serializable, Cloneable{
    private String pName;
    private int pID;
    private String pGender;
    private int pAge;
    private MedicalTestResult pMedical_test;

    public MedicalTestResult getMedical_test() {
        return pMedical_test;
    }

    public void setMedical_test(MedicalTestResult pMedical_test) {
        this.pMedical_test = pMedical_test;
    }


    public Patient(String pName, int pID, String pGender, int pAge, MedicalTestResult pMedical_test) {
        this.pName = pName;
        this.pID = pID;
        this.pGender = pGender;
        this.pAge = pAge;
        this.pMedical_test = pMedical_test;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public int getpID() {
        return pID;
    }

    public void setpID(int pID) {
        this.pID = pID;
    }

    public int getpAge() {
        return pAge;
    }

    public void setpAge(int pAge) {
        this.pAge = pAge;
    }

    public String getpGender() {
        return pGender;
    }

    public void setpGender(String pGender) {
        this.pGender = pGender;
    }


    @Override
    public Object clone() throws CloneNotSupportedException{
        Patient patient1 = null;
        try {
            patient1 = (Patient) super.clone();
        }catch (CloneNotSupportedException e){
            patient1 = new Patient(this.getpName(), this.getpID(), this.getpGender(), this.getpAge(), this.getMedical_test());
        }
        patient1.pMedical_test = (MedicalTestResult) this.pMedical_test.clone();
        return patient1;
    }
}
