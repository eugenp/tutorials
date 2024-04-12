package com.baeldung.boot.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.boot.Application;
import com.baeldung.boot.domain.Bar;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class SpringDataJPABarAuditIntegrationTest {

    private static Logger logger = LoggerFactory.getLogger(SpringDataJPABarAuditIntegrationTest.class);

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        logger.info("setUpBeforeClass()");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        logger.info("tearDownAfterClass()");
    }

    @Autowired
    @Qualifier("barSpringDataJpaService")
    private IBarService barService;

    @Autowired
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
    @WithMockUser(username = "tutorialuser")
    public final void whenBarsModified_thenBarsAudited() {
        Bar bar = new Bar("BAR1");
        barService.create(bar);
        assertEquals(bar.getCreatedDate(), bar.getModifiedDate());
        assertEquals("tutorialuser", bar.getCreatedBy(), bar.getModifiedBy());
        bar.setName("BAR2");
        bar = barService.update(bar);
        assertTrue(bar.getCreatedDate() < bar.getModifiedDate());
        assertEquals("tutorialuser", bar.getCreatedBy(), bar.getModifiedBy());
    }
}
