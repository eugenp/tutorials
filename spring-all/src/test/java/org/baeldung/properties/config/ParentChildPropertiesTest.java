package org.baeldung.properties.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({ @ContextConfiguration(classes = ParentConfig.class), @ContextConfiguration(classes = ChildConfig.class) })
public class ParentChildPropertiesTest {

    @Autowired
    private WebApplicationContext wac;

    @Test
    public void givenParentPropertySource_whenGetValue_thenCorrect() {
        final Environment childEnv = wac.getEnvironment();
        final Environment parentEnv = wac.getParent().getEnvironment();

        assertEquals(parentEnv.getProperty("parent.name"), "parent");
        assertNull(parentEnv.getProperty("child.name"));

        assertEquals(childEnv.getProperty("parent.name"), "parent");
        assertEquals(childEnv.getProperty("child.name"), "child");
    }
}
