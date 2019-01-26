package org.baeldung.persistence.multiple.dao.product;

import org.baeldung.persistence.multiple.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
