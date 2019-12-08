package org.baeldung.profiles;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringProfilesConfig.class }, loader = AnnotationConfigContextLoader.class)
public class SpringProfilesWithMavenPropertiesIntegrationTest {

    @Autowired
    DatasourceConfig datasourceConfig;

    @Test
    public void testSpringProfiles() {
        Assert.assertTrue(datasourceConfig instanceof DevDatasourceConfig);
    }
}