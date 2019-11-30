package com.baeldung.domain;

public interface OrderDomainService {
    
    void createOrder(Long amount);
    
    Order getOrder(Integer id);


}
