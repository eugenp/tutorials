package com.baeldung.jsonoptimization;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerShortNames {

    @JsonProperty("i")
    private long id;

    @JsonProperty("f")
    private String firstName;

    @JsonProperty("l")
    private String lastName;

    @JsonProperty("s")
    private String street;

    @JsonProperty("p")
    private String postalCode;

    @JsonProperty("c")
    private String city;

    @JsonProperty("a")
    private String state;

    @JsonProperty("o")
    private String phoneNumber;

    @JsonProperty("e")
    private String email;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, email, firstName, id, lastName, phoneNumber, postalCode, state, street);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CustomerShortNames)) {
            return false;
        }
        CustomerShortNames other = (CustomerShortNames) obj;
        return Objects.equals(city, other.city) && Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName) && id == other.id && Objects.equals(lastName, other.lastName) && Objects.equals(phoneNumber, other.phoneNumber)
            && Objects.equals(postalCode, other.postalCode) && Objects.equals(state, other.state) && Objects.equals(street, other.street);
    }

    @Override
    public String toString() {
        return "CustomerWithShorterAttributes [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", street=" + street + ", postalCode=" + postalCode + ", city=" + city + ", state=" + state + ", phoneNumber=" + phoneNumber + ", email=" + email
            + "]";
    }

    public static CustomerShortNames[] fromCustomers(Customer[] customers) {
        CustomerShortNames[] feedback = new CustomerShortNames[customers.length];

        for (int i = 0; i < customers.length; i++) {
            Customer aCustomer = customers[i];
            CustomerShortNames newOne = new CustomerShortNames();

            newOne.setId(aCustomer.getId());
            newOne.setFirstName(aCustomer.getFirstName());
            newOne.setLastName(aCustomer.getLastName());
            newOne.setStreet(aCustomer.getStreet());
            newOne.setCity(aCustomer.getCity());
            newOne.setPostalCode(aCustomer.getPostalCode());
            newOne.setState(aCustomer.getState());
            newOne.setPhoneNumber(aCustomer.getPhoneNumber());
            newOne.setEmail(aCustomer.getEmail());

            feedback[i] = newOne;
        }

        return feedback;
    }

}
