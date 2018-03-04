package com.baeldung.typesofdispring.config;

import com.baeldung.typesofdispring.model.Booking;
import com.baeldung.typesofdispring.model.Customer;
import com.baeldung.typesofdispring.model.Invoice;
import com.baeldung.typesofdispring.model.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.baeldung.typesofdispring")
public class AppConfig {
    @Bean
    public Customer getCustomer() {
        return new Customer();
    }

    @Bean
    public Product getProduct() {
        return new Product();
    }

    @Bean
    public Booking getBooking() {
        Booking booking = new Booking(getCustomer(), getProduct());
        booking.setInvoice(getInvoice());
        return booking;
    }

    @Bean
    public Invoice getInvoice() {
        return new Invoice();
    }

}
