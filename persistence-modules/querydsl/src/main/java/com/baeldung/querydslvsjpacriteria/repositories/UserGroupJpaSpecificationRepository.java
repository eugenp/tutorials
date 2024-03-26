package com.baeldung.querydslvsjpacriteria.repositories;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.baeldung.querydslvsjpacriteria.entities.UserGroup;
import com.baeldung.querydslvsjpacriteria.entities.UserGroup_;

public interface UserGroupJpaSpecificationRepository extends JpaRepository<UserGroup, Long>,
  JpaSpecificationExecutor<UserGroup> {

    default List<UserGroup> findAllWithNameInAnyList(List<String> names1, List<String> names2) {
        return findAll(specNameInAnyList(names1, names2));
    }

    default Specification<UserGroup> specNameInAnyList(List<String> names1, List<String> names2) {
        return (root, q, cb) -> cb.or(
            root.get(UserGroup_.name).in(names1),
            root.get(UserGroup_.name).in(names2)
        );
    }
}
