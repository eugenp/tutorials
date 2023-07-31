package com.baeldung.shallowdeepcopy;

class CustomerInfo implements Cloneable {
    private String address;
    private Double accountBalance;

    public CustomerInfo(String address, Double accountBalance) {
        this.address = address;
        this.accountBalance = accountBalance;
    }

    public CustomerInfo(CustomerInfo customerInfo) {
        this.accountBalance = customerInfo.accountBalance;
        this.address = customerInfo.address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}