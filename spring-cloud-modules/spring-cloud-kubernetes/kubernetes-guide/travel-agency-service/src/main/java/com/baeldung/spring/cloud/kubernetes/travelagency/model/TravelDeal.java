package com.baeldung.spring.cloud.kubernetes.travelagency.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigInteger;
import java.util.Date;

@Document(collection = "travel_deal")
public class TravelDeal {

    @Id
    private BigInteger id;

    private String destination;

    private String description;

    @Field("deal_price")
    private double dealPrice;

    @Field("old_price")
    private double oldPrice;

    @Field("departure_date")
    private Date departureDate;

    @Field("arrival_date")
    private Date arrivalDate;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(double dealPrice) {
        this.dealPrice = dealPrice;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    @Override
    public String toString() {
        return "TravelDeal{" + "id=" + id + ", destination='" + destination + '\'' + ", description='" + description + '\'' + ", dealPrice=" + dealPrice + ", oldPrice=" + oldPrice + ", departureDate=" + departureDate + ", arrivalDate=" + arrivalDate + '}';
    }
}
