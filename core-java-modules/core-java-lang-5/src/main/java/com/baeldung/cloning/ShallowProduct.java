package com.baeldung.cloning;

/**
 * Product with shallow cloning supported.
 *
 * @author Sachin.Raverkar
 */
public class ShallowProduct implements Cloneable {

    private String name;

    private String code;
    private ShallowProductCategory shallowProductCategory;

    public ShallowProduct(String name) {
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

    public ShallowProductCategory getShallowProductCategory() {
        return shallowProductCategory;
    }

    public void setShallowProductCategory(ShallowProductCategory shallowProductCategory) {
        this.shallowProductCategory = shallowProductCategory;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "ShallowProduct{" + "name='" + name + '\'' + ", code='" + code + '\'' + ", shallowProductCategory=" + shallowProductCategory + '}';
    }
}
