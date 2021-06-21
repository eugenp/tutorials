package com.baeldung.hexarch.domain;

public class Ticket {

    private String customerName;

    private String customerDob;

    private String ticketId;

    private String sourceCity;

    private String destCity;

    public Ticket(String customerName, String customerDob, String ticketId, String sourceCity, String destCity) {
        this.customerName = customerName;
        this.customerDob = customerDob;
        this.ticketId = ticketId;
        this.sourceCity = sourceCity;
        this.destCity = destCity;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerDob() {
        return customerDob;
    }

    public void setCustomerDob(String customerDob) {
        this.customerDob = customerDob;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getSourceCity() {
        return sourceCity;
    }

    public void setSourceCity(String sourceCity) {
        this.sourceCity = sourceCity;
    }

    public String getDestCity() {
        return destCity;
    }

    public void setDestCity(String destCity) {
        this.destCity = destCity;
    }

}
