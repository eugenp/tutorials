package com.baeldung.port;

import com.baeldung.domain.Order;

public interface OrderService {
    
    void createOrder(Long amount);
    
    Order getOrder(Integer id);


}
