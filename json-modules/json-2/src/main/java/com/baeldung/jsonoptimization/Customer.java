package com.baeldung.jsonoptimization;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Customer {

    private long id;
    private String firstName;
    private String lastName;
    private String street;
    private String postalCode;
    private String city;
    private String state;
    private String phoneNumber;
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
        if (!(obj instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) obj;
        return Objects.equals(city, other.city) && Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName) && id == other.id && Objects.equals(lastName, other.lastName) && Objects.equals(phoneNumber, other.phoneNumber)
            && Objects.equals(postalCode, other.postalCode) && Objects.equals(state, other.state) && Objects.equals(street, other.street);
    }

    @Override
    public String toString() {
        return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", street=" + street + ", postalCode=" + postalCode + ", city=" + city + ", state=" + state + ", phoneNumber=" + phoneNumber + ", email=" + email + "]";
    }

    public static Customer[] fromMockFile() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream jsonFile = new FileInputStream("src/test/resources/json_optimization_mock_data.json");
        Customer[] feedback = objectMapper.readValue(jsonFile, Customer[].class);
        return feedback;
    }
}
