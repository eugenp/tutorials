package com.baeldung.multiprocessorandwriter.processor;

import org.springframework.batch.item.ItemProcessor;

import com.baeldung.multiprocessorandwriter.model.Customer;

public class CustomerProcessorRouter implements ItemProcessor<Customer, Customer> {
    private final TypeAProcessor typeAProcessor;
    private final TypeBProcessor typeBProcessor;

    public CustomerProcessorRouter(TypeAProcessor typeAProcessor,
        TypeBProcessor typeBProcessor) {
        this.typeAProcessor = typeAProcessor;
        this.typeBProcessor = typeBProcessor;
    }

    @Override
    public Customer process(Customer customer) throws Exception {
        if ("A".equals(customer.getType())) {
            return typeAProcessor.process(customer);
        } else if ("B".equals(customer.getType())) {
            return typeBProcessor.process(customer);
        }
        return customer;
    }
}
