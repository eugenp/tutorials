package com.baeldung.memoryleaks.equalshashcode;

public class PersonOptimized {
    public String name;
     
    public PersonOptimized(String name) {
        this.name = name;
    }
     
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof PersonOptimized)) {
            return false;
        }
        PersonOptimized person = (PersonOptimized) o;
        return person.name.equals(name);
    }
     
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        return result;
    }}
