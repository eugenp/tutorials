package com.baeldung.spring.mail;

/**
 * Created by Olga on 8/22/2016.
 */
public class Order {

    public Order(String customerEmail,
                 String customerFirstName,
                 String customerLastName) {
        this.customerEmail = customerEmail;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
    }

    private String customerEmail;
    private String customerFirstName;
    private String customerLastName;

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }
}
