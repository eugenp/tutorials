package com.baeldung.domain;

import java.time.LocalDate;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Shipment {

    private ObjectId id;
    private LocalDate shippingDate;
    private Address address;

    public Shipment setShippingDate(LocalDate shippingDate) {
        this.shippingDate = shippingDate;
        return this;
    }

    public Shipment setAddress(Address address) {
        this.address = address;
        return this;
    }

}
