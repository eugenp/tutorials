package com.baeldung.dddcontexts.ordercontext.repository;

import com.baeldung.dddcontexts.ordercontext.model.CustomerOrder;

public interface CustomerOrderRepository {
    void saveCustomerOrder(CustomerOrder order);
}
