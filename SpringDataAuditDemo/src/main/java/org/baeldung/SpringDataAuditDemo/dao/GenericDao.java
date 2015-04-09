package org.baeldung.SpringDataAuditDemo.dao;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;

/**
 * This interface extends {@link org.springframework.data.repository.CrudRepository}. It may include methods common to the rest of the daos.
 * @param <T> defines entity type
 * @param <I> defines primary key type
 */
public interface GenericDao<T, I extends Serializable> extends CrudRepository<T, I> {
}