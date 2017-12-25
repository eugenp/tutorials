package com.baeldung.beaninjection.beantypes.Services;

import com.baeldung.beaninjection.beantypes.Interfaces.ICustomerService;
import com.baeldung.beaninjection.beantypes.models.Customer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("vipCustomerService")
public class CustomerService implements ICustomerService {
    @Override
    public Customer getCustomer(int id) {
        return new Customer(1, "tal", "avissar", "VIP");
    }
}
