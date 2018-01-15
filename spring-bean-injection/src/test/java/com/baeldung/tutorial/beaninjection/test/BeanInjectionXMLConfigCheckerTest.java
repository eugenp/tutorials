package com.baeldung.tutorial.beaninjection.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.tutorial.beaninjection.config.BeanInjectionConfig;
import com.baeldung.tutorial.beaninjection.main.BeanInjectionChecker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BeanInjectionConfig.class })
@ActiveProfiles(profiles = { "xmlconfig" })
public class BeanInjectionXMLConfigCheckerTest {

    @Test
    public final void givenUserInfo_whenInjectedFromXML_thenShowUser() {
        ApplicationContext context = new ClassPathXmlApplicationContext("app-config.xml");
        BeanInjectionChecker checker = context.getBean(BeanInjectionChecker.class);
        checker.showUser();
        assertTrue(true);
    }

}
