package com.baeldung.hibernate.pojo.generator;

import java.io.Serializable;
import java.util.Properties;
import java.util.stream.Stream;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

public class MyGenerator implements IdentifierGenerator, Configurable {

    private String prefix;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {

        Stream<String> ids = session.createQuery("select " + session.getEntityPersister(obj.getClass()
            .getName(), obj)
            .getIdentifierPropertyName() + " from "
            + obj.getClass()
                .getSimpleName())
            .stream();

        Long max = ids.map(o -> o.replace(prefix + "-", ""))
            .map(o -> Long.parseLong(o))
            .max(Long::compare)
            .orElse(0L);

        return prefix + "-" + (max + 1);
    }

    @Override
    public void configure(Type type, Properties properties, ServiceRegistry serviceRegistry) throws MappingException {
        prefix = properties.getProperty("prefix");
    }

}
