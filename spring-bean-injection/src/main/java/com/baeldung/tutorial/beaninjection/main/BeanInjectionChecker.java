package com.baeldung.tutorial.beaninjection.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.tutorial.beaninjection.beans.UserProfile;
import com.baeldung.tutorial.beaninjection.config.BeanInjectionConfig;

@Component
public class BeanInjectionChecker {

    @Autowired
    private UserProfile profile;

    private static final Logger LOGGER = LoggerFactory.getLogger(BeanInjectionConfig.class);

    public void showUser() {
        LOGGER.info(profile.getCredentials()
            .getUserName());
        LOGGER.info("City : " + profile.getDemographic()
            .getCity() + " State : "
            + profile.getDemographic()
                .getState()
            + " ZipCode : " + profile.getDemographic()
                .getZipCode());
    }
}
