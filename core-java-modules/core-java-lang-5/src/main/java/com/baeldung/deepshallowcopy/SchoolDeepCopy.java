package com.baeldung.deepshallowcopy;

public class SchoolDeepCopy  implements Cloneable{
    public String name;
    public SchoolDeepCopy(String name){
        this.name = name;
    }

    protected Object clone() throws  CloneNotSupportedException{
        return super.clone();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
