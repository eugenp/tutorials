package com.baeldung.hexagonalarchitecture.core.service;

import com.baeldung.hexagonalarchitecture.core.domain.Order;

public interface NotificationService {

    void notifyOrderCreation(Order order);

    void notifyOrderUpdation(Order order);

    void notifyOrderDeletion(Order order);

}
