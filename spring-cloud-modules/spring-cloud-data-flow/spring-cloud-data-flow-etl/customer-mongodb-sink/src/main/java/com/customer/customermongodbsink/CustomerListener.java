package com.customer.customermongodbsink;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(Sink.class)
public class CustomerListener {

    @Autowired
    private CustomerRepository repository;

    @StreamListener(Sink.INPUT)
    public void save(Customer customer) {
        repository.save(customer);
    }
}
