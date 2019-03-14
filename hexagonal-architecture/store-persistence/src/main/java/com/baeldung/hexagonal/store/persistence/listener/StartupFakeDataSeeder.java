package com.baeldung.hexagonal.store.persistence.listener;

import com.baeldung.hexagonal.store.core.context.customer.entity.Customer;
import com.baeldung.hexagonal.store.core.context.order.entity.Order;
import com.baeldung.hexagonal.store.core.context.order.entity.OrderProduct;
import com.baeldung.hexagonal.store.core.context.order.entity.OrderProductId;
import com.baeldung.hexagonal.store.core.context.order.entity.Product;
import com.baeldung.hexagonal.store.persistence.repo.customer.CustomerRepository;
import com.baeldung.hexagonal.store.persistence.repo.order.OrderProductRepository;
import com.baeldung.hexagonal.store.persistence.repo.order.OrderRepository;
import com.baeldung.hexagonal.store.persistence.repo.product.ProductRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StartupFakeDataSeeder implements ApplicationListener<ApplicationReadyEvent> {
    public static final int FAKE_CUSTOMER_COUNT = 10;
    public static final int FAKE_PRODUCT_COUNT = 40;
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private Faker faker = new Faker();

    @Autowired
    public StartupFakeDataSeeder(CustomerRepository customerRepository, OrderRepository orderRepository, OrderProductRepository orderProductRepository, ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.orderProductRepository = orderProductRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        seedData();
    }

    private void seedData() {
        List<Product> products = generateRandomProducts();
        for (int i = 0; i < FAKE_CUSTOMER_COUNT; i++) {
            Customer customer = new Customer();
            customer.setFirstname(faker.name().firstName());
            customer.setLastname(faker.name().lastName());
            customer.setBalance(faker.number().randomDouble(3, 0, 2000));
            customer.setEmail(faker.internet().emailAddress());
            Order order = generateRandomOrder(products);
            order.setCustomer(customer);
            order.setStatus(faker.code().asin());
            customer.addOrder(order);
            customerRepository.save(customer);
        }
    }

    private Order generateRandomOrder(List<Product> products) {
        Order order = new Order();
        long orderItems = faker.number().randomNumber(1, false);
        List<OrderProduct> orderProducts = new ArrayList<>();
        List<Product> addedProducts = new ArrayList<>();
        for (int i = 0; i < orderItems; i++) {
            Product product = null;
            while (product == null || addedProducts.contains(product)) {
                product = products.get(faker.random().nextInt(0, products.size() - 1));
            }
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setId(new OrderProductId(order, product));
            addedProducts.add(product);
            orderProduct.setQuantity((int) faker.number().randomNumber(1, false));
            orderProducts.add(orderProduct);
        }
        order.setOrderProducts(orderProducts);
        return order;
    }

    private List<Product> generateRandomProducts() {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < FAKE_PRODUCT_COUNT; i++) {
            Product product = new Product();
            product.setName(faker.commerce().productName());
            product.setPrice(faker.random().nextDouble() * 100);
            productRepository.save(product);
            products.add(product);
        }
        return products;
    }
}
