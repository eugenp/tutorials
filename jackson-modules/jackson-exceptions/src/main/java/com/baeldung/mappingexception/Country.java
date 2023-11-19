package com.baeldung.mappingexception;

import java.util.List;

public class Country {

    private String name;
    private List<String> cities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

}
