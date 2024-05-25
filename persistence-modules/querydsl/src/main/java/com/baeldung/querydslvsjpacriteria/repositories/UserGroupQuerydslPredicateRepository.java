package com.baeldung.querydslvsjpacriteria.repositories;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.baeldung.querydslvsjpacriteria.entities.QUserGroup;
import com.baeldung.querydslvsjpacriteria.entities.UserGroup;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

public interface UserGroupQuerydslPredicateRepository extends JpaRepository<UserGroup, Long>, QuerydslPredicateExecutor<UserGroup> {

    default List<UserGroup> findAllWithNameInAnyList(List<String> names1, List<String> names2) {
        return StreamSupport
          .stream(findAll(predicateInAnyList(names1, names2)).spliterator(), false)
          .collect(Collectors.toList());
    }

    default Predicate predicateInAnyList(List<String> names1, List<String> names2) {
        return new BooleanBuilder().and(QUserGroup.userGroup.name.in(names1))
          .or(QUserGroup.userGroup.name.in(names2));
    }
}
