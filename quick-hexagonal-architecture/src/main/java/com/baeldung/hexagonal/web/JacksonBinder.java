package com.baeldung.hexagonal.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * HK2 binder for providing a Jackson {@link ObjectMapper} singleton from the {@link ObjectMapperFactory}.
 */
class JacksonBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bindFactory(ObjectMapperFactory.class).to(ObjectMapper.class)
            .in(Singleton.class);
    }
}
