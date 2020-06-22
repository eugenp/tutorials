package com.baeldung.domain;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Shipment {

    private ObjectId id;
    private Date shippingDate;
    private Address address;

    public Shipment setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
        return this;
    }

    public Shipment setAddress(Address address) {
        this.address = address;
        return this;
    }

}
