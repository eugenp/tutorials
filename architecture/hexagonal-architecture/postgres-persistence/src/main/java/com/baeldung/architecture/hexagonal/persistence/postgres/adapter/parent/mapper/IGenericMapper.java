package com.baeldung.architecture.hexagonal.persistence.postgres.adapter.parent.mapper;

public interface IGenericMapper<M, E> {

    M entityToModel(E entity);
}
