package hexagonal.persistence.jpa.repository;

import hexagonal.persistence.jpa.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    OrderEntity findByOrderId(Integer orderId);
}
