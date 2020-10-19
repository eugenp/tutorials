package com.baeldung.hexagonalarchitecture.port;

import com.baeldung.hexagonalarchitecture.domain.Order;

public interface OrderStatusChangedNotifier {

    void orderStatusChanged(Order order);

}
