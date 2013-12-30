package org.baeldung.jackson.try1;

public class COrder {
    private String name;
    private String detail;

    //

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(final String detail) {
        this.detail = detail;
    }

    //

    @Override
    public String toString() {
        return "COrder [name=" + name + ", detail=" + detail + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
    }

}