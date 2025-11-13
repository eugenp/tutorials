package com.baeldung.spring.data.es.termsqueries.repository;

import com.baeldung.spring.data.es.termsqueries.model.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UserRepository extends ElasticsearchRepository<User, String> {
    List<User> findByRoleAndIsActive(String role, boolean isActive);
}
