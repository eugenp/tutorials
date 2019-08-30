package com.baeldung.hex.port;

import com.baeldung.hex.core.Order;

public interface OrderCompletedPort {
    void orderCompleted(Order order);
}
