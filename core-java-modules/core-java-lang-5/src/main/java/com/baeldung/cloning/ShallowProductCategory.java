package com.baeldung.cloning;

/**
 * Product category with shallow cloning supported.
 *
 * @author Sachin.Raverkar
 */
public class ShallowProductCategory implements Cloneable {

    private String name;

    private String code;

    public ShallowProductCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "ShallowProductCategory{" + "name='" + name + '\'' + ", code='" + code + '\'' + '}';
    }
}
