package com.baeldung.cloning;

/**
 * Product with deep cloning supported.
 *
 * @author Sachin.Raverkar
 */
public class DeepProduct implements Cloneable {

    private final String name;

    private String code;
    private DeepProductCategory deepProductCategory;

    public DeepProduct(String name) {
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
        DeepProduct clonedDeepProduct = (DeepProduct) super.clone();
        clonedDeepProduct.setDeepProductCategory((DeepProductCategory) getDeepProductCategory().clone());
        return clonedDeepProduct;
    }

    @Override
    public String toString() {
        return "DeepProduct{" + "name='" + name + '\'' + ", code='" + code + '\'' + ", deepProductCategory=" + deepProductCategory + '}';
    }

    public DeepProductCategory getDeepProductCategory() {
        return deepProductCategory;
    }

    public void setDeepProductCategory(DeepProductCategory deepProductCategory) {
        this.deepProductCategory = deepProductCategory;
    }
}
