package com.baeldung.pojo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

@XStreamAlias("CustomerAddressDetails")
public class CustomerAddressDetails {

    private List<AddressDetails> addressDetails;

    private String firstName;

    private String lastName;

    private int age;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<AddressDetails> getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(List<AddressDetails> addressDetails) {
        this.addressDetails = addressDetails;
    }
}
