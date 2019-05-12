package com.baeldung.persistence.dao.common;

import java.io.Serializable;

import org.baeldung.persistence.dao.AbstractJpaDAO;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GenericJpaDao<T extends Serializable> extends AbstractJpaDAO<T> implements IGenericDao<T> {
    //
}