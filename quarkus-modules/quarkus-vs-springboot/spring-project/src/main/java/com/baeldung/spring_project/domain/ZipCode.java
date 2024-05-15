package com.baeldung.spring_project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "zipcode")
public class ZipCode implements Persistable<String> {

    @Id
    private String zip;
    private String type;
    private String city;
    private String state;
    private String county;
    private String timezone;

    @Transient
    private boolean persisted;

    public String getZip() {
        return zip;
    }

    void setZip(String zip) {
        this.zip = zip;
        this.persisted = true;
    }

    public void setId(String zip) {
        this.zip = zip;
        this.persisted = false;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @JsonIgnore
    @Override public String getId() {
        return zip;
    }

    @JsonIgnore
    @Override public boolean isNew() {
        return !persisted;
    }
}
