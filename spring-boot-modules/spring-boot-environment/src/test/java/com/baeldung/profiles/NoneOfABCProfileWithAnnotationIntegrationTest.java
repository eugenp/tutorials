package com.baeldung.profiles;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration(classes = { SpringProfilesConfig.class }, loader = AnnotationConfigContextLoader.class)
public class NoneOfABCProfileWithAnnotationIntegrationTest {
    @Autowired
    NoneOfThreeProfilesComponent noneOfThreeProfilesComponent;

    @Test
    public void testSpringProfiles() {
        Assert.assertNotNull(noneOfThreeProfilesComponent);
    }
}