package com.baeldung.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Customer {
    private String id;
    private String telephone;
    private List<String> favorites;
    private Map<String, Boolean> communicationPreferences;

    public Customer() {
    }

    public Customer(String id, String telephone, List<String> favorites, Map<String, Boolean> communicationPreferences) {
        this(telephone, favorites, communicationPreferences);
        this.id = id;
    }

    public Customer(String telephone, List<String> favorites, Map<String, Boolean> communicationPreferences) {
        this.telephone = telephone;
        this.favorites = favorites;
        this.communicationPreferences = communicationPreferences;
    }

    public static Customer fromCustomer(Customer customer) {
        return new Customer(customer.getId(), customer.getTelephone(), customer.getFavorites(), customer.getCommunicationPreferences());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Map<String, Boolean> getCommunicationPreferences() {
        return communicationPreferences;
    }

    public void setCommunicationPreferences(Map<String, Boolean> communicationPreferences) {
        this.communicationPreferences = communicationPreferences;
    }

    public List<String> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<String> favorites) {
        this.favorites = favorites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
