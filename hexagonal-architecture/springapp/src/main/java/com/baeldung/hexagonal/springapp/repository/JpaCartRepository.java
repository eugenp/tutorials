package com.baeldung.hexagonal.springapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.core.Cart;
import com.baeldung.hexagonal.core.CartRepository;
import com.baeldung.hexagonal.springapp.entity.CartEntity;
import com.baeldung.hexagonal.springapp.mapper.CartMapper;

@Repository
public interface JpaCartRepository extends CartUpdater, CartRepository, JpaRepository<CartEntity, Long> {

    @Override default Optional<Cart> findCartOfCustomer(Long customerId) {
        return findCartByCustomerId(customerId).map(CartMapper.INSTANCE::toModel);
    }

    @Query("SELECT cart FROM CartEntity cart JOIN FETCH cart.items JOIN FETCH cart.customer WHERE cart.customer.id = :customerId")
    Optional<CartEntity> findCartByCustomerId(@Param("customerId") Long customerId);

    @Override default void save(Cart cart) {
        update(CartMapper.INSTANCE.toEntity(cart));
    }
}
