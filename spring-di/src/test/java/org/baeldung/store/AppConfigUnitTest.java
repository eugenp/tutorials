package org.baeldung.store;


import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class AppConfigUnitTest {

    @Autowired
    @Qualifier("storeThroughConstructorInjection")
    private Store storeByConstructorInjection;
    
    @Autowired
    @Qualifier("storeThroughSetterInjection")
    private Store storeBySetterInjection;

    @Test
    public void givenValidXmlConfig_WhenInjectStoreByConstructorInjection_ThenBeanIsNotNull() {
        assertNotNull(storeByConstructorInjection);
        assertNotNull(storeByConstructorInjection.getItem());
    }

    @Test
    public void givenValidXmlConfig_WhenInjectStoreBySetterInjection_ThenBeanIsNotNull() {
        assertNotNull(storeBySetterInjection);
        assertNotNull(storeByConstructorInjection.getItem());
    }
}
