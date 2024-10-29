package com.baeldung.filternestedlist;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class FilterNestedListUnitTest {

    @Test
    void givenUsersWithOrders_whenFilterWithPriceThresholdUsingLoop_thenReturnFilteredUsers() {
        double priceThreshold = 500.0;
        Order order1 = new Order("Laptop", 600.0);
        Order order2 = new Order("Phone", 300.0);
        Order order3 = new Order("Nintendo Switch", 510.0);
        Order order4 = new Order("Monitor", 200.0);
        User user1 = new User("Alice", Arrays.asList(order1, order4));
        User user2 = new User("Bob", Arrays.asList(order3));
        User user3 = new User("Mity", Arrays.asList(order2));
        List<User> users = Arrays.asList(user1, user2, user3);

        List<User> filteredUsers = new ArrayList<>();
        for (User user : users) {
            for (Order order : user.getOrders()) {
                if (order.getPrice() > priceThreshold) {
                    filteredUsers.add(user);
                    break;
                }
            }
        }

        assertEquals(2, filteredUsers.size());
        assertEquals("Alice", filteredUsers.get(0).getName());
        assertEquals("Bob", filteredUsers.get(1).getName());
    }

    @Test
    void givenUsersWithOrders_whenFilterByProductUsingStreams_thenReturnUsersWhoOrderedLaptop() {
        double priceThreshold = 500.0;
        Order order1 = new Order("Laptop", 600.0);
        Order order2 = new Order("Phone", 300.0);
        Order order3 = new Order("Nintendo Switch", 510.0);
        Order order4 = new Order("Monitor", 200.0);
        User user1 = new User("Alice", Arrays.asList(order1, order4));
        User user2 = new User("Bob", Arrays.asList(order3));
        User user3 = new User("Mity", Arrays.asList(order2));
        List<User> users = Arrays.asList(user1, user2, user3);

        List<User> filteredUsers = users.stream()
            .filter(user -> user.getOrders().stream()
                .anyMatch(order -> order.getPrice() > priceThreshold))
            .collect(Collectors.toList());

        assertEquals(2, filteredUsers.size());
        assertEquals("Alice", filteredUsers.get(0).getName());
        assertEquals("Bob", filteredUsers.get(1).getName());
    }

    @Test
    void givenUsersWithOrders_whenFilterByProductAndPriceUsingStreams_thenReturnFilteredUsers() {
        String productToFilter = "Laptop";
        double priceThreshold = 500.0;
        Order order1 = new Order("Laptop", 600.0);
        Order order2 = new Order("Phone", 300.0);
        Order order3 = new Order("Nintendo Switch", 510.0);
        Order order4 = new Order("Monitor", 200.0);
        User user1 = new User("Alice", Arrays.asList(order1, order4));
        User user2 = new User("Bob", Arrays.asList(order3));
        User user3 = new User("Mity", Arrays.asList(order2));
        List<User> users = Arrays.asList(user1, user2, user3);

        List<User> filteredUsers = users.stream()
            .filter(user -> user.getOrders().stream()
                .anyMatch(order -> order.getProduct().equals(productToFilter) && order.getPrice() > priceThreshold))
            .collect(Collectors.toList());

        assertEquals(1, filteredUsers.size());
        assertEquals("Alice", filteredUsers.get(0).getName());
    }

    @Test
    void givenUsersWithOrders_whenFilterWithCustomPredicate_thenReturnUsersWithExpensiveOrders() {
        double priceThreshold = 500.0;
        Order order1 = new Order("Laptop", 600.0);
        Order order2 = new Order("Phone", 300.0);
        Order order3 = new Order("Nintendo Switch", 510.0);
        Order order4 = new Order("Monitor", 200.0);
        User user1 = new User("Alice", Arrays.asList(order1, order4));
        User user2 = new User("Bob", Arrays.asList(order3));
        User user3 = new User("Mity", Arrays.asList(order2));
        List<User> users = Arrays.asList(user1, user2, user3);

        Predicate<User> hasExpensiveOrder = user -> user.getOrders().stream()
            .anyMatch(order -> order.getPrice() > priceThreshold);

        List<User> filteredUsers = users.stream()
            .filter(hasExpensiveOrder)
            .collect(Collectors.toList());

        assertEquals(2, filteredUsers.size());
        assertEquals("Alice", filteredUsers.get(0).getName());
        assertEquals("Bob", filteredUsers.get(1).getName());
    }

    @Test
    void givenUsersWithOrders_whenFilterOrdersAndPreserveUsers_thenReturnUsersWithFilteredOrders() {
        double priceThreshold = 500.0;
        Order order1 = new Order("Laptop", 600.0);
        Order order2 = new Order("Phone", 300.0);
        Order order3 = new Order("Nintendo Switch", 510.0);
        Order order4 = new Order("Monitor", 200.0);
        User user1 = new User("Alice", Arrays.asList(order1, order4));
        User user2 = new User("Bob", Arrays.asList(order3));
        User user3 = new User("Mity", Arrays.asList(order2));
        List<User> users = Arrays.asList(user1, user2, user3);

        List<User> filteredUsersWithLimitedOrders = users.stream()
            .map(user -> {
                List<Order> filteredOrders = user.getOrders().stream()
                    .filter(order -> order.getPrice() > priceThreshold)
                    .collect(Collectors.toList());
                user.setOrders(filteredOrders);
                return user;
            })
            .filter(user -> !user.getOrders().isEmpty())
            .collect(Collectors.toList());

        assertEquals(2, filteredUsersWithLimitedOrders.size());
        assertEquals(1, filteredUsersWithLimitedOrders.get(0).getOrders().size());
        assertEquals(1, filteredUsersWithLimitedOrders.get(1).getOrders().size());
    }

    @Test
    void givenUsersWithOrders_whenFilterUsingFlatMap_thenReturnFilteredUsers() {
        double priceThreshold = 500.0;
        Order order1 = new Order("Laptop", 600.0);
        Order order2 = new Order("Phone", 300.0);
        Order order3 = new Order("Nintendo Switch", 510.0);
        Order order4 = new Order("Monitor", 200.0);
        User user1 = new User("Alice", Arrays.asList(order1, order4));
        User user2 = new User("Bob", Arrays.asList(order3));
        User user3 = new User("Mity", Arrays.asList(order2));
        List<User> users = Arrays.asList(user1, user2, user3);

        List<User> filteredUsers = users.stream()
            .flatMap(user -> user.getOrders().stream()
                .filter(order -> order.getPrice() > priceThreshold)
                .map(order -> user))
            .distinct()
            .collect(Collectors.toList());

        assertEquals(2, filteredUsers.size());
        assertEquals("Alice", filteredUsers.get(0).getName());
        assertEquals("Bob", filteredUsers.get(1).getName());
    }

    @Test
    void givenUsersWithNullOrEmptyOrders_whenFilter_thenIgnoreUsersWithNoValidOrders() {
        double priceThreshold = 500.0;
        Order order1 = new Order("Laptop", 600.0);
        Order order2 = new Order("Phone", 300.0);
        Order order3 = new Order("Nintendo Switch", 510.0);
        User user1 = new User("Alice", Arrays.asList(order1, order2));
        User user2 = new User("Bob", Arrays.asList(order3));
        User user3 = new User("Charlie", new ArrayList<>());  // User with empty orders
        List<User> users = Arrays.asList(user1, user2, user3);

        List<User> filteredUsers = users.stream()
            .filter(user -> user.getOrders() != null && !user.getOrders().isEmpty())
            .filter(user -> user.getOrders().stream()
                .anyMatch(order -> order.getPrice() > priceThreshold))
            .collect(Collectors.toList());

        assertEquals(2, filteredUsers.size());
        assertEquals("Alice", filteredUsers.get(0).getName());
        assertEquals("Bob", filteredUsers.get(1).getName());
    }
}

class User {

    private String name;
    private List<Order> orders;

    public User(String name, List<Order> orders) {
        this.name = name;
        this.orders = orders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}


class Order {

    private String product;
    private double price;

    public Order(String product, double price) {
        this.product = product;
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}