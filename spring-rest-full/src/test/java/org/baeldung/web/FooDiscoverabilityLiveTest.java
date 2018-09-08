package org.baeldung.web;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.baeldung.common.web.AbstractDiscoverabilityLiveTest;
import org.baeldung.persistence.model.Foo;
import org.baeldung.spring.ConfigIntegrationTest;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
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
