package com.baeldung.drools.model;

import java.util.ArrayList;
import java.util.List;

public class Result {
    private String value;
    private List<String> facts = new ArrayList<>();

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getFacts() {
        return facts;
    }

    public void setFacts(List<String> facts) {
        this.facts = facts;
    }
    
    public void addFact(String fact) {
        this.facts.add(fact);
    }
    
    
}
