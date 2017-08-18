package com.baeldung.setterdi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = Config.class)
public class SetterBeanInjectionJavaConfigUnitTest {
    @Autowired
    ApplicationContext context;

    @Test
    public void givenJavaConfig_whenApplicationStarts_thenSpringShouldCreateCarWithDependencies() {
        DependencyInjectionTestHelper.checkCarInitializedWithDependencies(context);
    }
}
