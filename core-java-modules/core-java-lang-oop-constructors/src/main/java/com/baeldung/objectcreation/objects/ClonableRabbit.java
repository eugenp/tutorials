package com.baeldung.objectcreation.objects;

public class ClonableRabbit implements Cloneable {

    String name = "";

    public ClonableRabbit(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
