package com.baeldung.dozer;

public class Person3 {
    private String name;
    private String dtob;

    public Person3() {

    }

    public Person3(String name, String dtob) {
        super();
        this.name = name;
        this.dtob = dtob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDtob() {
        return dtob;
    }

    public void setDtob(String dtob) {
        this.dtob = dtob;
    }

    @Override
    public String toString() {
        return "Person3 [name=" + name + ", dtob=" + dtob + "]";
    }

}
