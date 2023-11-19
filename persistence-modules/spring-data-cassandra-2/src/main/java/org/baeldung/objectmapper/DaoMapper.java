package org.baeldung.objectmapper;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import org.baeldung.objectmapper.dao.CounterDao;
import org.baeldung.objectmapper.dao.UserDao;

@Mapper
public interface DaoMapper {

    @DaoFactory
    UserDao getUserDao(@DaoKeyspace CqlIdentifier keyspace);

    @DaoFactory
    CounterDao getUserCounterDao(@DaoKeyspace CqlIdentifier keyspace);
}