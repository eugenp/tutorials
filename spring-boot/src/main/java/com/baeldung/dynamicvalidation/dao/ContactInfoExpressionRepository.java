package com.baeldung.dynamicvalidation.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.dynamicvalidation.model.ContactInfoExpression;

public interface ContactInfoExpressionRepository extends JpaRepository<ContactInfoExpression, String> {

}
