package com.baeldung.framework;

import com.baeldung.application.adapter.OracleDataFeeder;
import com.baeldung.domain.port.DataFeeder;
import com.google.inject.AbstractModule;

public class Module extends AbstractModule {

    @Override
    protected void configure() {
        bind(DataFeeder.class).to(OracleDataFeeder.class);
    }

}
