package com.baeldung.model;

public class CustomerDto {
    public long id;
    public String firstName;
    public String lastName;
    public String address;
    public String email;
    public String phone;
    //...
    public String phone99;
    
    public CustomerDto() {}
    
    public CustomerDto(Customer c) {
        this.id = c.id;
        this.firstName = c.firstName;
        this.lastName = c.lastName;
        this.address = c.address;
        this.email = c.email;
        this.phone = c.phone;
    }
    
    public Customer convertToEntity() {
        Customer c = new Customer();
        c.id = id;
        c.firstName = firstName;
        c.lastName = lastName;
        c.address = address;
        c.email = email;
        c.phone = phone;
        return c;
    }
}
