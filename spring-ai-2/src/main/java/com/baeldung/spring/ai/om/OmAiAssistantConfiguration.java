package com.baeldung.spring.ai.om;

import java.util.List;
import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

@Configuration
public class OmAiAssistantConfiguration {
    @Bean
    @Description("Create an order. The Order ID is identified with orderID. The order quantity is identified by orderQuantity."
        + "The user is identified by userID, "
    + "The order quantity should be a positive whole number")
    public Function<CreateOrderRequest, Long> createOrderFn(OrderManagementService orderManagementService) {
        return createOrderRequest -> orderManagementService.createOrder(createOrderRequest.orderInfo());
    }
    @Bean
    @Description("get all the orders of an user. The user ID is identified with userID.")
    public Function<GetOrderRequest, List<OrderInfo>> getUserOrders(OrderManagementService orderManagementService) {
        return getOrderRequest -> orderManagementService.getAllUserOrders(getOrderRequest.userID()).get();
    }
}

record GetOrderRequest(String userID) {

}

record CreateOrderRequest(OrderInfo orderInfo) {

}

record OrderResponse(Long orderID) {

}

