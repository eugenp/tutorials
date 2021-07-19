package com.baeldung.properties.parentchild;

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

import com.baeldung.properties.parentchild.config.ChildConfig;
import com.baeldung.properties.parentchild.config.ParentConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({ @ContextConfiguration(classes = ParentConfig.class), @ContextConfiguration(classes = ChildConfig.class) })
public class ParentChildPropertySourcePropertiesIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    @Test
    public void givenPropertySource_whenGetPropertyUsingEnv_thenCorrect() {
        final Environment childEnv = wac.getEnvironment();
        final Environment parentEnv = wac.getParent().getEnvironment();

        assertEquals(parentEnv.getProperty("parent.name"), "parent");
        assertNull(parentEnv.getProperty("child.name"));

        assertEquals(childEnv.getProperty("parent.name"), "parent");
        assertEquals(childEnv.getProperty("child.name"), "child");
    }

    @Test
    public void givenPropertySource_whenGetPropertyUsingValueAnnotation_thenCorrect() {
        final ChildValueHolder childValueHolder = wac.getBean(ChildValueHolder.class);
        final ParentValueHolder parentValueHolder = wac.getParent().getBean(ParentValueHolder.class);

        assertEquals(parentValueHolder.getParentName(), "parent");
        assertEquals(parentValueHolder.getChildName(), "-");

        assertEquals(childValueHolder.getParentName(), "parent");
        assertEquals(childValueHolder.getChildName(), "child");
    }

}
