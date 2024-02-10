package com.baeldung.cloning;

/**
 * Product category with deep cloning supported.
 *
 * @author Sachin.Raverkar
 */
public class DeepProductCategory implements Cloneable {

    private String name;

    private String code;

    public DeepProductCategory(String name) {
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
        return "DeepProductCategory{" + "name='" + name + '\'' + ", code='" + code + '\'' + '}';
    }
}
