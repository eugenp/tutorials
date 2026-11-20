package com.baeldung.web;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import com.baeldung.common.web.AbstractDiscoverabilityLiveTest;
import com.baeldung.persistence.model.Foo;
import com.baeldung.spring.ConfigIntegrationTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ConfigIntegrationTest.class }, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("test")
public class FooDiscoverabilityLiveTest extends AbstractDiscoverabilityLiveTest<Foo> {

    public FooDiscoverabilityLiveTest() {
        super(Foo.class);
    }

    // API

    @Override
    public final void create() {
        create(new Foo(randomAlphabetic(6)));
    }

    @Override
    public final String createAsUri() {
        return createAsUri(new Foo(randomAlphabetic(6)));
    }

}
