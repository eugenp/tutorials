package com.baeldung.hexagonalarchitecture.adapter.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonalarchitecture.core.domain.Order;
import com.baeldung.hexagonalarchitecture.core.service.NotificationService;

@Service
public class LogBasedNotificationService implements NotificationService {

    private Logger logger = LoggerFactory.getLogger(LogBasedNotificationService.class);

    @Override
    public void notifyOrderCreation(Order order) {
        logger.info("Order created. " + order);
    }

    @Override
    public void notifyOrderUpdation(Order order) {
        logger.info("Order updated. " + order);

    }

    @Override
    public void notifyOrderDeletion(Order order) {
        logger.info("Order deleted. " + order);
    }

}
