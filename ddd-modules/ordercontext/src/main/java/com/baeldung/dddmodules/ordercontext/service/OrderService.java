package com.baeldung.dddmodules.ordercontext.service;

import com.baeldung.dddmodules.ordercontext.model.CustomerOrder;
import com.baeldung.dddmodules.ordercontext.repository.CustomerOrderRepository;
import com.baeldung.dddmodules.sharedkernel.service.ApplicationService;

public interface OrderService extends ApplicationService {
    void placeOrder(CustomerOrder order);

    void setOrderRepository(CustomerOrderRepository orderRepository);
}
