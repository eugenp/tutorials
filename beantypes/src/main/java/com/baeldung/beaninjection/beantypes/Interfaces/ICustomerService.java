package com.baeldung.beaninjection.beantypes.Interfaces;
import com.baeldung.beaninjection.beantypes.models.Customer;
public interface ICustomerService {

    Customer GetCustomer(int id);
}
