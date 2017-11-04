package com.baeldung.beaninjection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=SpringConfig.class, loader=AnnotationConfigContextLoader.class)
public class BeanInjectionIntegrationTest {

    @Autowired
    private DbConnection dbConnection;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void givenSpringConfigIsUsed_WhenSpringContextIsCreated_ThenConfiguredBeansArePresent() {
        assertNotNull("dbConnection must not be null!", dbConnection);
        assertNotNull("productRepository must not be null!", productRepository);
        assertNotNull(
                "productRepository must have a dbConnection wired!",
                productRepository.getDbConnection());
    }

}