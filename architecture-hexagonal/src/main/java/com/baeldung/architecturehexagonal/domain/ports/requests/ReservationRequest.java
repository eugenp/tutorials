package com.baeldung.architecturehexagonal.domain.ports.requests;

public class ReservationRequest {
    public String getRestaurantName() {
        return restaurantName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Integer getNumberOfPersons() {
        return numberOfPersons;
    }

    private final String restaurantName;
    private final String customerName;
    private final Integer numberOfPersons;

    public ReservationRequest(String restaurantName, String customerName, Integer numberOfPersons) {
        this.restaurantName = restaurantName;
        this.customerName = customerName;
        this.numberOfPersons = numberOfPersons;
    }
}
