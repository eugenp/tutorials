package com.baeldung;

import com.baeldung.hex.adapter.OrderCompletedEmailingAdapter;
import com.baeldung.hex.adapter.OrderCompletedPrintingAdapter;
import com.baeldung.hex.adapter.SimpleOrderPlacingAdapter;
import com.baeldung.hex.core.OrderManager;
import com.baeldung.hex.core.OrderManagerImpl;
import com.baeldung.hex.port.OrderCompletedPort;
import com.baeldung.hex.port.PlaceOrderPort;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class Spring5HexagonalApplication {
    public static void main(String[] args) {
        SpringApplication.run(Spring5HexagonalApplication.class, args);
    }

    @Bean
    PlaceOrderPort getSimpleOrderPlacingAdapter(OrderManager orderManager) {
        SimpleOrderPlacingAdapter simpleOrderPlacingAdapter = new SimpleOrderPlacingAdapter(orderManager);
        simpleOrderPlacingAdapter.createSomeOrders();
        return simpleOrderPlacingAdapter;
    }

    @Bean
    OrderManager getOrderManager(Set<OrderCompletedPort> orderCompletedPortSet) {
        return new OrderManagerImpl(orderCompletedPortSet);
    }

    @Bean
    Set<OrderCompletedPort> orderCompletedPortSet() {
        return new HashSet<>(Arrays.asList(new OrderCompletedEmailingAdapter(), new OrderCompletedPrintingAdapter()));
    }
}
