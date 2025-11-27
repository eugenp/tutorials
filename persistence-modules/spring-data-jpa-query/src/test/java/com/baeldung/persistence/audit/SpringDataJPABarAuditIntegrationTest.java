package com.baeldung.persistence.audit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional; // <-- IMPORT ADDED

import com.baeldung.persistence.config.PersistenceTestConfig;
import com.baeldung.persistence.model.Bar;
import com.baeldung.persistence.service.IBarService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { PersistenceTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class SpringDataJPABarAuditIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(SpringDataJPABarAuditIntegrationTest.class);

    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
        logger.info("setUpBeforeClass()");
    }

    @AfterAll
    public static void tearDownAfterClass(){
        logger.info("tearDownAfterClass()");
    }

    @Autowired
    @Qualifier("barSpringDataJpaService")
    private IBarService barService;

    @Autowired
    @Qualifier("jpaEntityManager")
    private EntityManagerFactory entityManagerFactory;

    private EntityManager em;

    @BeforeEach
    public void setUp() throws Exception {
        logger.info("setUp()");
        em = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    public void tearDown() throws Exception {
        logger.info("tearDown()");
        em.close();
    }

    @Test
    @Transactional // <-- FIX: Ensures auditing context is available
    @WithMockUser(username = "tutorialuser")
    public final void whenBarsModified_thenBarsAudited() {
        Bar bar = new Bar("BAR1");
        
        // 1. Create (INSERT)
        bar = barService.create(bar); // Ensure we get the updated managed entity
        
        // Assertions after INSERT
        assertEquals(bar.getCreatedDate(), bar.getModifiedDate(), "Created and Modified dates should be equal after insert.");
        assertEquals("tutorialuser", bar.getCreatedBy(), "CreatedBy should be 'tutorialuser'.");
        assertEquals("tutorialuser", bar.getModifiedBy(), "ModifiedBy should be 'tutorialuser' after insert.");

        bar.setName("BAR2");
        
        // 2. Update (UPDATE)
        bar = barService.update(bar);
        
        // Assertions after UPDATE
        assertTrue(bar.getCreatedDate() < bar.getModifiedDate(), "Modified date should be greater than created date after update.");
        assertEquals("tutorialuser", bar.getCreatedBy(), "CreatedBy should remain 'tutorialuser'.");
        assertEquals("tutorialuser", bar.getModifiedBy(), "ModifiedBy should remain 'tutorialuser' after update.");
    }
}
