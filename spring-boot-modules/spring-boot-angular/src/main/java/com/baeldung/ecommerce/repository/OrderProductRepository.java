package com.baeldung.ecommerce.repository;

import com.baeldung.ecommerce.model.OrderProduct;
import com.baeldung.ecommerce.model.OrderProductPK;
import org.springframework.data.repository.CrudRepository;

public interface OrderProductRepository extends CrudRepository<OrderProduct, OrderProductPK> {
}
