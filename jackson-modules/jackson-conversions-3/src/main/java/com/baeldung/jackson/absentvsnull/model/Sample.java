package com.baeldung.jackson.absentvsnull.model;

import java.util.List;

public class Sample {

    private Long id;
    private String name;
    private int amount = 1;
    private List<String> keys;
    private List<Integer> values;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> names) {
        this.keys = names;
    }

    public List<Integer> getValues() {
        return values;
    }

    public void setValues(List<Integer> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "{id: '" + id + "', name: '" + name + "', amount: " + amount + ", keys: " + keys + ", values: " + values + "}";
    }
}
