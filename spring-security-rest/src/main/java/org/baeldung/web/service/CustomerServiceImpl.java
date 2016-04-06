package org.baeldung.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.baeldung.persistence.model.Customer;
import org.baeldung.persistence.model.Order;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private HashMap<String, Customer> customerMap;
    private HashMap<String, Order> customerOneOrderMap;
    private HashMap<String, Order> customerTwoOrderMap;

    public CustomerServiceImpl() {

        customerMap = new HashMap<>();
        customerOneOrderMap = new HashMap<>();
        customerTwoOrderMap = new HashMap<>();

        final Customer customerOne = new Customer("10A", "Jane", "ABC Company");
        final Customer customerTwo = new Customer("20B", "Bob", "XYZ Company");

        customerOneOrderMap.put("001A", new Order("001A", 150.00, 25));
        customerOneOrderMap.put("002A", new Order("002A", 250.00, 15));

        customerTwoOrderMap.put("002B", new Order("002B", 550.00, 325));
        customerTwoOrderMap.put("002B", new Order("002B", 450.00, 525));

        customerOne.setOrders(customerOneOrderMap);
        customerTwo.setOrders(customerTwoOrderMap);
        customerMap.put("10A", customerOne);
        customerMap.put("20B", customerTwo);

    }

    @Override
    public List<Customer> allCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getCustomerDetail(final String customerId) {
        return customerMap.get(customerId);
    }

    @Override
    public List<Order> getAllOrdersForCustomer(final String customerId) {
        return new ArrayList<>(customerMap.get(customerId).getOrders().values());
    }

    @Override
    public Order getOrderByIdForCustomer(final String customerId, final String orderId) {

        final List<Order> orders = (List<Order>) customerMap.get(customerId).getOrders().values();
        Order selectedOrder = null;
        for (final Order order : orders) {
            if (order.getId().equals(orderId)) {
                selectedOrder = order;
            }
        }
        return selectedOrder;

    }

}
