package com.baeldung.controller.repository;

import com.baeldung.entity.Person;
import com.baeldung.entity.QPerson;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

public interface PersonRepository
        extends JpaRepository<Person, Long>, QueryDslPredicateExecutor<Person>, QuerydslBinderCustomizer<QPerson> {
    @Override default void customize(final QuerydslBindings bindings, final QPerson root) {
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::eq);
    }
}
