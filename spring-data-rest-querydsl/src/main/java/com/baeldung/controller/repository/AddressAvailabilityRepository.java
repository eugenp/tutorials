package com.baeldung.controller.repository;

import com.baeldung.entity.AddressAvailability;
import com.baeldung.entity.QAddressAvailability;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public interface AddressAvailabilityRepository
        extends JpaRepository<AddressAvailability, Long>, QueryDslPredicateExecutor<AddressAvailability>,
        QuerydslBinderCustomizer<QAddressAvailability> {
    @Override default void customize(final QuerydslBindings bindings, final QAddressAvailability root) {
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::eq);
    }
}
