package com.baeldung.injection.type;

import com.baeldung.injection.Runner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Runner.class)
public class ConstructorInjectionComponentTest {

        @Autowired
        private ConstructorInjectionComponent component;

        @Test
        public void whenInjectedWithConstructor_ThenFieldNotNull() {
                Object injectableService = Whitebox.getInternalState(component, "injectableService");
                Assert.assertNotNull(injectableService);
        }

        @Test
        public void whenInjectedWithConstructorAndServiceCalled_ThenProperMessageReceived() {
                Assert.assertEquals(component.reuseService(), "Operation has been performed with: constructor injection");
        }
}