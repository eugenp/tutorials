package com.baeldung.hibernate.persistjson;

import java.io.IOException;
import java.util.Map;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "Customers")
public class Customer {

    @Id
    private int id;

    private String firstName;

    private String lastName;

    private String customerAttributeJSON;

    @Convert(converter = HashMapConverter.class)
    private Map<String, Object> customerAttributes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getCustomerAttributeJSON() {
        return customerAttributeJSON;
    }

    public void setCustomerAttributeJSON(String customerAttributeJSON) {
        this.customerAttributeJSON = customerAttributeJSON;
    }

    public Map<String, Object> getCustomerAttributes() {
        return customerAttributes;
    }

    public void setCustomerAttributes(Map<String, Object> customerAttributes) {
        this.customerAttributes = customerAttributes;
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public void serializeCustomerAttributes() throws JsonProcessingException {
        this.customerAttributeJSON = objectMapper.writeValueAsString(customerAttributes);
    }

    public void deserializeCustomerAttributes() throws IOException {
        this.customerAttributes = objectMapper.readValue(customerAttributeJSON, Map.class);
    }

}
