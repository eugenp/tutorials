package com.baeldung.orderservice;

import com.baeldung.orderservice.client.OrderDTO;
import com.baeldung.orderservice.client.OrderResponse;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class OrderService {


    private List<Order> orders = Arrays.asList(

            new Order(1, 1, "A101", "2020/02/14"),
            new Order(2, 1, "A101", "2020/02/14"),
            new Order(3, 2, "A150", "2020/02/17"));

    @GetMapping
    public List<Order> getAllOrders() {
        return orders;
    }

    @GetMapping("/{id}")
    public List<Order> getOrdersByCustomer(@PathVariable int id) {
        return orders.stream()
                .filter(order -> order.getCustomerId() == id).collect(Collectors.toList());
    }

    @PostMapping("/create")
    public OrderResponse createOrder(@RequestBody OrderDTO request) {

        int lastIndex = orders.size();
        Order order = new Order();
        order.setId(lastIndex + 1);
        order.setCustomerId(request.getCustomerId());
        order.setItemId(request.getItemId());
        String date = DateFormatUtils.format(new Date(), "yyyy/MM/dd");
        order.setDate(date);

        return new OrderResponse(order.getId(), order.getItemId(), "CREATED");
    }

}
