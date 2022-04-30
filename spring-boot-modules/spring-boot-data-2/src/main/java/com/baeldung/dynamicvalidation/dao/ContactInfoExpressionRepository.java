package com.baeldung.dynamicvalidation.dao;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import com.baeldung.dynamicvalidation.model.ContactInfoExpression;

public interface ContactInfoExpressionRepository extends Repository<ContactInfoExpression, String> {
    Optional<ContactInfoExpression> findById(String id);
}
