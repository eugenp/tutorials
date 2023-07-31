package com.baeldung.shallowdeepcopy;

class Customer implements Cloneable {
    private String firstName;
    private String lastName;
    private CustomerInfo customerInfo;

    public Customer(String firstName, String lastName, CustomerInfo customerInfo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.customerInfo = customerInfo;
    }

    public Customer(Customer originalCustomer) {
        this.firstName = originalCustomer.firstName;
        this.lastName = originalCustomer.lastName;
        this.customerInfo = new CustomerInfo(originalCustomer.customerInfo);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Customer clonedCustomer = (Customer) super.clone();
        clonedCustomer.customerInfo = (CustomerInfo) customerInfo.clone();
        return clonedCustomer;
    }
}
