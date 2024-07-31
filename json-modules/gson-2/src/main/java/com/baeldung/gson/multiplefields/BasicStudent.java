package com.baeldung.gson.multiplefields;

import java.util.Objects;

import com.google.gson.annotations.SerializedName;

public class BasicStudent {

    private String name;

    private transient String major;

    @SerializedName("major")
    private String concentration;

    public BasicStudent() {

    }

    public BasicStudent(String name, String major, String concentration) {
        this.name = name;
        this.major = major;
        this.concentration = concentration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof BasicStudent))
            return false;
        BasicStudent student = (BasicStudent) o;
        return Objects.equals(name, student.name) && Objects.equals(major, student.major) && Objects.equals(concentration, student.concentration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, major, concentration);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }
}
