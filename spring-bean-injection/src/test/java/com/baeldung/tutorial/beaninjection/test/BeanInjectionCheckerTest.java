package com.baeldung.tutorial.beaninjection.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.tutorial.beaninjection.config.BeanInjectionConfig;
import com.baeldung.tutorial.beaninjection.main.BeanInjectionChecker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BeanInjectionConfig.class })
@ActiveProfiles(value = { "beansconfig" })
public class BeanInjectionCheckerTest {

    @Autowired
    private BeanInjectionChecker checker;

    @Test
    public final void givenUserInfo_whenInjectedFromCode_thenShowUser() {
        checker.showUser();
        assertTrue("user name does not match",checker.getUserProfile().getCredentials().getUserName().equals("mdas"));
        assertTrue("city does not match",checker.getUserProfile().getDemographic().getCity().equals("Chicago"));
        assertTrue("state does not match",checker.getUserProfile().getDemographic().getState().equals("Illinois"));
        assertTrue("zip code does not match",checker.getUserProfile().getDemographic().getZipCode() == 60600);
    }

}
