package com.baeldung.properties.value;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ClassNotManagedBySpringIntegrationTest {

    @MockBean
    private InitializerBean initializerBean;

    @Before
    public void init() {
        when(initializerBean.initClass())
          .thenReturn(new ClassNotManagedBySpring("This is only sample value", "Another configured value"));
    }

    @Test
    public void givenInitializerBean_whenInvokedInitClass_shouldInitialize() throws Exception {

        //given
        ClassNotManagedBySpring classNotManagedBySpring = initializerBean.initClass();

        //when
        String initializedValue = classNotManagedBySpring.getCustomVariable();
        String anotherCustomVariable = classNotManagedBySpring.getAnotherCustomVariable();

        //then
        assertEquals("This is only sample value", initializedValue);
        assertEquals("Another configured value", anotherCustomVariable);

    }

}