package com.baeldung.persistence.audit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.persistence.model.Bar;
import com.baeldung.persistence.model.Bar.OPERATION;
import com.baeldung.persistence.service.IBarService;
import com.baeldung.spring.config.PersistenceTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceTestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class JPABarAuditIntegrationTest {

    private static final Logger logger = LoggerFactory.getLogger(JPABarAuditIntegrationTest.class);

    @BeforeClass
    public static void setUpBeforeClass() {
        logger.info("setUpBeforeClass()");
    }

    @AfterClass
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

    @Before
    public void setUp() throws Exception {
        logger.info("setUp()");
        em = entityManagerFactory.createEntityManager();
    }

    @After
    public void tearDown() throws Exception {
        logger.info("tearDown()");
        em.close();
    }

    @Test
    public final void whenBarsModified_thenBarsAudited() {

        // insert BAR1
        Bar bar1 = new Bar("BAR1");
        barService.create(bar1);

        // update BAR1
        bar1.setName("BAR1a");
        barService.update(bar1);

        // insert BAR2
        Bar bar2 = new Bar("BAR2");
        barService.create(bar2);

        // update BAR1
        bar1.setName("BAR1b");
        barService.update(bar1);

        // get BAR1 and BAR2 from the DB and check the audit values
        // detach instances from persistence context to make sure we fire db
        em.detach(bar1);
        em.detach(bar2);
        bar1 = barService.findOne(bar1.getId());
        bar2 = barService.findOne(bar2.getId());

        assertNotNull(bar1);
        assertNotNull(bar2);
        assertEquals(OPERATION.UPDATE, bar1.getOperation());
        assertEquals(OPERATION.INSERT, bar2.getOperation());
        assertTrue(bar1.getTimestamp() > bar2.getTimestamp());

        barService.deleteById(bar1.getId());
        barService.deleteById(bar2.getId());

    }

}