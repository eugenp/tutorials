package com.baeldung.controller.repository;

import com.baeldung.entity.Address;
import com.baeldung.entity.QAddress;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public interface AddressRepository
        extends JpaRepository<Address, Long>, QuerydslPredicateExecutor<Address>, QuerydslBinderCustomizer<QAddress> {
    @Override
    default void customize(final QuerydslBindings bindings, final QAddress root) {
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::eq);
    }
}
