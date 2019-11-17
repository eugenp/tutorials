package com.baeldung.ddd.layers.application;

import com.baeldung.ddd.layers.application.request.AddProductRequest;
import com.baeldung.ddd.layers.application.request.CreateOrderRequest;
import com.baeldung.ddd.layers.application.response.CreateOrderResponse;
import com.baeldung.ddd.layers.domain.service.OrderService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    CreateOrderResponse createOrder(@RequestBody final CreateOrderRequest createOrderRequest) {
        final ObjectId id = orderService.createOrder(createOrderRequest.getProduct());

        return new CreateOrderResponse(id.toString());
    }

    @PostMapping(value = "/{id}/products", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void addProduct(@PathVariable final ObjectId id, @RequestBody final AddProductRequest addProductRequest) {
        orderService.addProduct(id, addProductRequest.getProduct());
    }

    @DeleteMapping(value = "/{id}/products", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void deleteProduct(@PathVariable final ObjectId id, @RequestParam final UUID productId) {
        orderService.deleteProduct(id, productId);
    }

    @PostMapping("/{id}/complete")
    void completeOrder(@PathVariable final ObjectId id) {
        orderService.completeOrder(id);
    }
}
