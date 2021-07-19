package com.baeldung.store;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Separate unit test class where only one Item object is available for 
 * autowiring. If the ioc-context.xml were used for autowiring by type, there 
 * would be multiple qualifying Item objects, causing a failure.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/ioc-context-by-type.xml")
public class XmlAppConfigByTypeUnitTest {

    @Autowired
    @Qualifier("xml-store-by-autowire-type")
    private Store storeByAutowireInjectionByType;

    @Test
    public void givenValidXmlConfig_WhenInjectStoreByAutowireInjectionByType_ThenBeanIsNotNull() {
        assertNotNull(storeByAutowireInjectionByType);
        assertNotNull(storeByAutowireInjectionByType.getItem());
    }
}
