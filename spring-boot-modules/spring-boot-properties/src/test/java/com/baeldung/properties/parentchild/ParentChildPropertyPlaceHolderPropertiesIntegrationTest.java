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

import com.baeldung.properties.parentchild.config.ChildConfig2;
import com.baeldung.properties.parentchild.config.ParentConfig2;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({ @ContextConfiguration(classes = ParentConfig2.class), @ContextConfiguration(classes = ChildConfig2.class) })
public class ParentChildPropertyPlaceHolderPropertiesIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    @Test
    public void givenPropertyPlaceHolder_whenGetPropertyUsingEnv_thenCorrect() {
        final Environment childEnv = wac.getEnvironment();
        final Environment parentEnv = wac.getParent().getEnvironment();

        assertNull(parentEnv.getProperty("parent.name"));
        assertNull(parentEnv.getProperty("child.name"));

        assertNull(childEnv.getProperty("parent.name"));
        assertNull(childEnv.getProperty("child.name"));
    }

    @Test
    public void givenPropertyPlaceHolder_whenGetPropertyUsingValueAnnotation_thenCorrect() {
        final ChildValueHolder childValueHolder = wac.getBean(ChildValueHolder.class);
        final ParentValueHolder parentValueHolder = wac.getParent().getBean(ParentValueHolder.class);

        assertEquals(parentValueHolder.getParentName(), "parent");
        assertEquals(parentValueHolder.getChildName(), "-");

        assertEquals(childValueHolder.getParentName(), "-");
        assertEquals(childValueHolder.getChildName(), "child");
    }

}
