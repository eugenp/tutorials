package com.baeldung.inheritancevscomposition;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.config.TestJpaConfig;
import com.baeldung.inheritancevscomposition.inheritance.Developer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestJpaConfig.class)
public class DemoIntegrationTest {

    @Test
    public void givenInheritanceOrCompositionRelationship_whenEntitiesDefined_thenTablesCreated() {
        // Observe the table creation SQL
    }

}
