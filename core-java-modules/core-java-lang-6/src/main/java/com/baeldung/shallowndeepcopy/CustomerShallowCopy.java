package com.baeldung.shallowndeepcopy;

public class CustomerShallowCopy implements Cloneable {

    public AddressShallowCopy addressShallowCopy;
    public String customerName;

    public CustomerShallowCopy(AddressShallowCopy addressShallowCopy, String customerName) {
        super();
        this.addressShallowCopy = addressShallowCopy;
        this.customerName = customerName;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
