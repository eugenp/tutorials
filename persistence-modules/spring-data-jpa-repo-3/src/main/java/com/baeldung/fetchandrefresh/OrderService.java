package com.baeldung.fetchandrefresh;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class OrderService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findOrdersByCustomerAndDateRangeUsingCriteriaAPI(String customerName, LocalDate startDate, LocalDate endDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);

        Predicate customerPredicate = criteriaBuilder.equal(root.get("customerName"), customerName);
        Predicate dateRangePredicate = criteriaBuilder.between(root.get("orderDate"), startDate, endDate);

        criteriaQuery.where(customerPredicate, dateRangePredicate);

        return entityManager.createQuery(criteriaQuery)
            .getResultList();
    }

    public void updateOrderName(long orderId, String newName) {
        Order order = orderRepository.findById(orderId)
            .map(existingOrder -> {
                existingOrder.setName(newName);
                return existingOrder;
            })
            .orElseGet(() -> {
                return null;
            });

        if (order != null) {
            try {
                orderRepository.save(order);
            } catch (OptimisticLockException e) {
                // Refresh the entity and potentially retry the update
                entityManager.refresh(order);
                // Consider adding logic to handle retries or notify the user about the conflict
            }
        }
    }
}
