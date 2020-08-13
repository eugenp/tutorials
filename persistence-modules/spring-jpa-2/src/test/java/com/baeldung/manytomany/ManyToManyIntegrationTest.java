package com.baeldung.manytomany;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ManyToManyTestConfiguration.class)
@DirtiesContext
public class ManyToManyIntegrationTest {

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void contextStarted() {
    }

}
