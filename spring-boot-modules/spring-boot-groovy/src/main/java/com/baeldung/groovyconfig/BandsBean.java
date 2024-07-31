package com.baeldung.groovyconfig;

import java.util.ArrayList;
import java.util.List;

public class BandsBean {
    
    private List<String> bandsList = new ArrayList<>();

    public List<String> getBandsList() {
        return bandsList;
    }

    public void setBandsList(List<String> bandsList) {
        this.bandsList = bandsList;
    }
}
