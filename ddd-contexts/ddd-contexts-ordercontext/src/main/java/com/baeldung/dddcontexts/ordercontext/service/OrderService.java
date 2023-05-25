package com.baeldung.dddcontexts.ordercontext.service;

import com.baeldung.dddcontexts.ordercontext.model.CustomerOrder;
import com.baeldung.dddcontexts.ordercontext.repository.CustomerOrderRepository;
import com.baeldung.dddcontexts.sharedkernel.service.ApplicationService;

public interface OrderService extends ApplicationService {
    void placeOrder(CustomerOrder order);

    void setOrderRepository(CustomerOrderRepository orderRepository);
}
