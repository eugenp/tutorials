package com.baeldung.changevalue.hibernate;

import org.hibernate.integrator.spi.Integrator;
import org.hibernate.jpa.boot.spi.IntegratorProvider;

import java.util.Collections;
import java.util.List;

public class HibernateEventListenerIntegratorProvider implements IntegratorProvider {
    @Override
    public List<Integrator> getIntegrators() {
        return Collections.singletonList(new HibernateEventListenerIntegrator());
    }
}