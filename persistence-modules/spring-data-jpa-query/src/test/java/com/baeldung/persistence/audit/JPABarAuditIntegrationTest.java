package com.baeldung.persistence.audit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.persistence.config.PersistenceTestConfig;
import com.baeldung.persistence.model.Bar;
import com.baeldung.persistence.model.Bar.OPERATION;
import com.baeldung.persistence.service.IBarService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

// Replaces @RunWith(SpringJUnit4ClassRunner.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { PersistenceTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class JPABarAuditIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(JPABarAuditIntegrationTest.class);

    @BeforeAll // Replaces @BeforeClass (must be static)
    public static void setUpBeforeClass() {
        logger.info("setUpBeforeClass()");
    }

    @AfterAll // Replaces @AfterClass (must be static)
    public static void tearDownAfterClass(){
        logger.info("tearDownAfterClass()");
    }

    @Autowired
    @Qualifier("barJpaService")
    private IBarService barService;

    @Autowired
    @Qualifier("jpaEntityManager")
    private EntityManagerFactory entityManagerFactory;

    private EntityManager em;

    @BeforeEach // Replaces @Before
    public void setUp() throws Exception {
        logger.info("setUp()");
        em = entityManagerFactory.createEntityManager();
    }

    @AfterEach // Replaces @After
    public void tearDown() throws Exception {
        logger.info("tearDown()");
        em.close();
    }

    @Test // Replaces @org.junit.Test
    public final void whenBarsModified_thenBarsAudited() {

        // insert BAR1
        Bar bar1 = new Bar("BAR1");
        barService.create(bar1);

        // update BAR1 (This should trigger @PreUpdate on bar1)
        bar1.setName("BAR1a");
        barService.update(bar1);

        // insert BAR2 (This should trigger @PrePersist on bar2)
        Bar bar2 = new Bar("BAR2");
        barService.create(bar2);

        // update BAR1 (This should trigger @PreUpdate again on bar1)
        bar1.setName("BAR1b");
        barService.update(bar1);

        // get BAR1 and BAR2 from the DB and check the audit values
        em.detach(bar1);
        em.detach(bar2);
        bar1 = barService.findOne(bar1.getId());
        bar2 = barService.findOne(bar2.getId());

        // JUnit 5 Assertions
        assertNotNull(bar1);
        assertNotNull(bar2);
        
        // bar1 was updated last, so its operation should be UPDATE
        assertEquals(OPERATION.UPDATE, bar1.getOperation(), "Bar1's operation should be UPDATE.");
        
        // bar2 was inserted last without subsequent update, so its operation should be INSERT
        assertEquals(OPERATION.INSERT, bar2.getOperation(), "Bar2's operation should be INSERT.");
        
        // The timestamp on bar1 (last update) should be greater than bar2's timestamp (last insert)
        assertTrue(bar1.getTimestamp() > bar2.getTimestamp(), "Bar1's timestamp should be newer than Bar2's.");

        // Cleanup: delete (Note: @PreRemove will fire, but the audit state is deleted with the entity)
        barService.deleteById(bar1.getId());
        barService.deleteById(bar2.getId());
    }
}
