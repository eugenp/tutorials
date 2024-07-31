package com.baeldung.importannotation.animal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BugConfig.class)
class BugConfigUnitTest {

    @Autowired
    ApplicationContext context;

    @Test
    void givenImportInComponent_whenLookForBean_shallFindIt() {
        assertTrue(context.containsBean("bug"));
        assertNotNull(context.getBean(Bug.class));
    }
}
