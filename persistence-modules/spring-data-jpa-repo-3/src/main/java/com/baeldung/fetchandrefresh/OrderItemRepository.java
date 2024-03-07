package com.baeldung.fetchandrefresh;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository  extends JpaRepository<OrderItem, Long> {

}
