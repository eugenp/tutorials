package org.baeldung.web.service;

import java.util.List;

import org.baeldung.persistence.model.Customer;
import org.baeldung.persistence.model.Order;

public interface CustomerService {

    List<Customer> allCustomers();

    Customer getCustomerDetail(final String id);

    List<Order> getAllOrdersForCustomer(String customerId);

    Order getOrderByIdForCustomer(String customerId, String orderId);

}
