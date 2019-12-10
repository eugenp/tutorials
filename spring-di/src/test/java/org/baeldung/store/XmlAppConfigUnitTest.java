package org.baeldung.store;


import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/ioc-context.xml")
public class XmlAppConfigUnitTest {
    
    @Autowired
    @Qualifier("xml-store-by-constructor")
    private Store storeByConstructorInjection;
    
    @Autowired
    @Qualifier("xml-store-by-setter")
    private Store storeBySetterInjection;

    @Autowired
    @Qualifier("xml-store-by-autowire-name")
    private Store storeByAutowireInjectionByName;
    
    @Autowired
    @Qualifier("xml-store-by-setter-lazy")
    private Store storeBySetterInjectionLazy;

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

    @Test
    public void givenValidXmlConfig_WhenInjectStoreByAutowireInjectionByName_ThenBeanIsNotNull() {
        assertNotNull(storeByAutowireInjectionByName);
        assertNotNull(storeByAutowireInjectionByName.getItem());
    }

    @Test
    public void givenValidXmlConfig_WhenInjectStoreBySetterInjectionLazy_ThenBeanIsNotNull() {
        assertNotNull(storeBySetterInjectionLazy);
        assertNotNull(storeByConstructorInjection.getItem());
    }
}
