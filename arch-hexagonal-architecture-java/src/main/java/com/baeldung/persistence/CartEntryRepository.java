package com.baeldung.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CartEntryRepository extends JpaRepository<CartEntry, Long>, JpaSpecificationExecutor<CartEntry> {
    CartEntry findByCartAndProduct(Cart cart, Product product);
}
