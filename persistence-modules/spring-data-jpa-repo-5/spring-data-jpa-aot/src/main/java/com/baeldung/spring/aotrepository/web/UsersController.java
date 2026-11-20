package com.baeldung.spring.aotrepository.web;

import com.baeldung.spring.aotrepository.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static org.springframework.http.ResponseEntity.ok;

@Controller
public class UsersController {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final InventoryRepository inventoryRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public UsersController(UserRepository userRepository, AddressRepository addressRepository,
                           InventoryRepository inventoryRepository, OrderRepository orderRepository,
                           ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.inventoryRepository = inventoryRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;

        System.out.println("===== DEBUG REPOSITORY CLASSES =====");
        System.out.println(userRepository.getClass());
        System.out.println(addressRepository.getClass());
        System.out.println(inventoryRepository.getClass());
        System.out.println(orderRepository.getClass());
        System.out.println(productRepository.getClass());
    }

    @GetMapping("get-user")
    public ResponseEntity<String> getUser() {
        return ok(userRepository.findAll().toString());
    }
}
