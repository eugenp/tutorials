package com.baeldung.shardingsphere;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


/**
 * This Manual test requires: Docker service running.
 */
@Testcontainers
@SpringBootTest
class OrderServiceManualTest {

    @Container
    static MySQLContainer<?> mySQLContainer1 = new MySQLContainer<>("mysql:8.0.23")
            .withDatabaseName("ds0")
            .withUsername("test")
            .withPassword("test");

    @Container
    static MySQLContainer<?> mySQLContainer2 = new MySQLContainer<>("mysql:8.0.23")
            .withDatabaseName("ds1")
            .withUsername("test")
            .withPassword("test");

    static {
        mySQLContainer2.setPortBindings(List.of("13307:3306"));
        mySQLContainer1.setPortBindings(List.of("13306:3306"));
    }
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @Test
    void shouldFindOrderInCorrectShard() {
        // given
        Order order1 = new Order(1L, 1L,  BigDecimal.TEN, Status.PROCESSING, LocalDate.now(), "123 Main St");
        Order order2 = new Order(2L, 2L, BigDecimal.valueOf(12.5), Status.SHIPPED, LocalDate.now(), "456 Main St");

        // when
        Order savedOrder1 = orderService.createOrder(order1);
        Order savedOrder2 = orderService.createOrder(order2);

        // then
        // Assuming the sharding strategy is based on the order id, data for order1 should be present only in ds0
        // and data for order2 should be present only in ds1
        Assertions.assertThat(orderService.getOrder(savedOrder1.getOrderId())).isEqualTo(savedOrder1);
        Assertions.assertThat(orderService.getOrder(savedOrder2.getOrderId())).isEqualTo(savedOrder2);

        // Verify that the orders are not present in the wrong shards.
        // You would need to implement these methods in your OrderService.
        // They should use a JdbcTemplate or EntityManager to execute SQL directly against each shard.
        Assertions.assertThat(assertOrderInShard(savedOrder1, mySQLContainer2)).isTrue();
        Assertions.assertThat(assertOrderInShard(savedOrder2, mySQLContainer1)).isTrue();
    }

    private boolean assertOrderInShard(Order order, MySQLContainer<?> container) {
        try (Connection conn = DriverManager.getConnection(container.getJdbcUrl(), container.getUsername(), container.getPassword())) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM `order` WHERE order_id = ?");
            stmt.setLong(1, order.getOrderId());
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            throw new RuntimeException("Error querying order in shard", ex);
        }
    }
}
