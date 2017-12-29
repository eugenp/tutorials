package com.baeldung.beaninjection.beantypes.Services;

import com.baeldung.beaninjection.beantypes.Interfaces.ICustomerService;
import com.baeldung.beaninjection.beantypes.models.Customer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("businessCustomerService")
public class BusinessCustomerService implements ICustomerService {

        @Override
        public Customer getCustomer(int id) {
        return new Customer(2, "John", "herold", "BUSINESS");
    }
}