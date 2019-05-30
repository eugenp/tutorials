package com.baeldung.hexagonal.fake;

import com.baeldung.hexagonal.persistence.PersonFactory;
import com.baeldung.hexagonal.persistence.PersonRepository;
import javax.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class FakeBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(FakePersonFactory.class).to(PersonFactory.class)
            .in(Singleton.class);
        bind(FakePersonRepository.class).to(PersonRepository.class)
            .in(Singleton.class);
    }
}
