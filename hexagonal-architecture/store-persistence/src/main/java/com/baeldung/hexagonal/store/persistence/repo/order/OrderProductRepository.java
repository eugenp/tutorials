package com.baeldung.hexagonal.store.persistence.repo.order;

import com.baeldung.hexagonal.store.core.context.order.entity.OrderProduct;
import com.baeldung.hexagonal.store.core.context.order.entity.OrderProductId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends CrudRepository<OrderProduct, OrderProductId> {
}
