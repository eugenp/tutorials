package org.baeldung.core;

import org.baeldung.spring.config.CoreConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { CoreConfig.class }, loader = AnnotationConfigContextLoader.class)
public class PropertiesViaXmlIntegrationTest {

    @Test
    public final void givenContextIsInitialized_thenNoException() {
        //
    }

}
