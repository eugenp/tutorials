package com.baeldung.typesofdispring.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Copyright (c) 2016. CodeGen Ltd. - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Created by darshana on 3/3/2018.
 */
@Component
public class BookingWithAutowired {
    private Customer customer;
    private Product product;
    private Invoice invoice;

    @Autowired
    public BookingWithAutowired(Customer customer, Product product) {
        this.customer = customer;
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    @Autowired
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
