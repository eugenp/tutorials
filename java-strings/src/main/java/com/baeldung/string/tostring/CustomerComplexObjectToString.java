package com.baeldung.string.tostring;

public class CustomerComplexObjectToString extends Customer {
    private Order orders;
 
    public Order getOrders() {
        return orders;
    }
    public void setOrders(Order orders) {
        this.orders = orders;
    }
    @Override
    public String toString() {
	return "Customer [orders=" + orders + ", getFirstName()=" + getFirstName()
	                + ", getLastName()=" + getLastName() + "]";
    }    
}