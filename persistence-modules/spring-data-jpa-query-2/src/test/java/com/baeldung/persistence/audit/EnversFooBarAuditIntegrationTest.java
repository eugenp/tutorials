package com.baeldung.persistence.audit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.persistence.model.Bar;
import com.baeldung.persistence.model.Foo;
import com.baeldung.persistence.service.IBarAuditableService;
import com.baeldung.persistence.service.IFooAuditableService;
import com.baeldung.spring.config.PersistenceTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceTestConfig.class }, loader = AnnotationConfigContextLoader.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class EnversFooBarAuditIntegrationTest {

    private static Logger logger = LoggerFactory.getLogger(EnversFooBarAuditIntegrationTest.class);

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        logger.info("setUpBeforeClass()");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        logger.info("tearDownAfterClass()");
    }

    @Autowired
    @Qualifier("fooHibernateAuditableService")
    private IFooAuditableService fooService;

    @Autowired
    @Qualifier("barHibernateAuditableService")
    private IBarAuditableService barService;

    @Autowired
    private SessionFactory sessionFactory;

    private Session session;

    @Before
    public void setUp() throws Exception {
        logger.info("setUp()");
        makeRevisions();
        session = sessionFactory.openSession();
    }

    @After
    public void tearDown() throws Exception {
        logger.info("tearDown()");
        session.close();
    }

    private void makeRevisions() {
        final Bar bar = rev1();
        rev2(bar);
        rev3(bar);
        rev4(bar);
    }

    // REV #1: insert BAR & FOO1
    private Bar rev1() {
        final Bar bar = new Bar("BAR");
        final Foo foo1 = new Foo("FOO1");
        foo1.setBar(bar);
        fooService.create(foo1);
        return bar;
    }

    // REV #2: insert FOO2 & update BAR
    private void rev2(final Bar bar) {
        final Foo foo2 = new Foo("FOO2");
        foo2.setBar(bar);
        fooService.create(foo2);
    }

    // REV #3: update BAR
    private void rev3(final Bar bar) {

        bar.setName("BAR1");
        barService.update(bar);
    }

    // REV #4: insert FOO3 & update BAR
    private void rev4(final Bar bar) {

        final Foo foo3 = new Foo("FOO3");
        foo3.setBar(bar);
        fooService.create(foo3);
    }

    @Test
    public final void whenFooBarsModified_thenFooBarsAudited() {

        List<Bar> barRevisionList;
        List<Foo> fooRevisionList;

        // test Bar revisions

        barRevisionList = barService.getRevisions();

        assertNotNull(barRevisionList);
        assertEquals(4, barRevisionList.size());

        assertEquals("BAR", barRevisionList.get(0)
                .getName());
        assertEquals("BAR", barRevisionList.get(1)
                .getName());
        assertEquals("BAR1", barRevisionList.get(2)
                .getName());
        assertEquals("BAR1", barRevisionList.get(3)
                .getName());

        assertEquals(1, barRevisionList.get(0)
                .getFooSet()
                .size());
        assertEquals(2, barRevisionList.get(1)
                .getFooSet()
                .size());
        assertEquals(2, barRevisionList.get(2)
                .getFooSet()
                .size());
        assertEquals(3, barRevisionList.get(3)
                .getFooSet()
                .size());

        // test Foo revisions

        fooRevisionList = fooService.getRevisions();
        assertNotNull(fooRevisionList);
        assertEquals(3, fooRevisionList.size());
        assertEquals("FOO1", fooRevisionList.get(0)
                .getName());
        assertEquals("FOO2", fooRevisionList.get(1)
                .getName());
        assertEquals("FOO3", fooRevisionList.get(2)
                .getName());
    }

}
