package com.baeldung.shallowndeepcopy;

public class CustomerDeepCopy implements Cloneable {

    public AddressDeepCopy addressDeepCopy;
    public String customerName;

    public CustomerDeepCopy(AddressDeepCopy addressDeepCopy, String customerName) {
        super();
        this.addressDeepCopy = addressDeepCopy;
        this.customerName = customerName;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        CustomerDeepCopy cust = (CustomerDeepCopy) super.clone();
        cust.addressDeepCopy = (AddressDeepCopy) addressDeepCopy.clone();
        return cust;
    }
}
