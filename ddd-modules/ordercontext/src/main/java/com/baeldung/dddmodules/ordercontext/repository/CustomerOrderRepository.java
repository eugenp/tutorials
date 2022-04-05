package com.baeldung.dddmodules.ordercontext.repository;

import com.baeldung.dddmodules.ordercontext.model.CustomerOrder;

public interface CustomerOrderRepository {
    void saveCustomerOrder(CustomerOrder order);
}
