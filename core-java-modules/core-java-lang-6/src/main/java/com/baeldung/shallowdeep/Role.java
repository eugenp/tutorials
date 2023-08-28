package com.baeldung.shallowdeep;

public class Role implements Cloneable {

    String title;

    Role( String title ) {
        this.title = title;
    }

    @Override
    public Role clone() throws CloneNotSupportedException {
        Role cRole = (Role) super.clone();
        return cRole;
    }
}
