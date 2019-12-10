package com.baeldung.dependency.ioc;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/ioc-context.xml")
public class XmlAppConfigTest {
    
    @Autowired
    @Qualifier("xml-store-by-constructor")
    private Store storeByConstructorInjection;

    @Test
    public void givenValidXmlConfig_WhenInjectStoreByConstructorInject_ThenBeanIsNotNull() {
        assertNotNull(storeByConstructorInjection);
    }
}
