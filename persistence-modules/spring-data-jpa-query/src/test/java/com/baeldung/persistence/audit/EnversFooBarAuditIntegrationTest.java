package com.baeldung.persistence.audit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
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
import com.baeldung.persistence.config.PersistenceTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceTestConfig.class }, loader = AnnotationConfigContextLoader.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class EnversFooBarAuditIntegrationTestIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnversFooBarAuditIntegrationTestIT.class);

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
    public void setUp() {
        LOGGER.info("setUp()");
        makeRevisions();
        session = sessionFactory.openSession();
    }

    @After
    public void tearDown() {
        LOGGER.info("tearDown()");
        session.close();
    }

    private void makeRevisions() {
        // Bar must be managed to be updated with new Fobs. 
        // We use the returned managed Bar from rev1.
        Bar bar = rev1();
        
        // Rev2 updates Foo, which implicitly updates Bar. We must re-fetch Bar to see the change.
        bar = rev2(bar); 
        
        // Rev3 updates Bar name directly.
        rev3(bar);
        
        // Rev4 updates Foo, which implicitly updates Bar. We must re-fetch Bar to see the change.
        rev4(bar);
    }

    // REV #1: insert BAR & FOO1
    private Bar rev1() {
        final Bar bar = new Bar("BAR");
        final Foo foo1 = new Foo("FOO1");
        foo1.setBar(bar);
        // The bar object is returned as the managed entity after the Foo (and its association to Bar) is created
        fooService.create(foo1); 
        return bar;
    }

    // REV #2: insert FOO2 & update BAR
    // Returns the managed Bar object after the operation
    private Bar rev2(Bar bar) { 
        final Foo foo2 = new Foo("FOO2");
        foo2.setBar(bar);
        fooService.create(foo2);
        
        // FIX: Re-fetch Bar to ensure the FooSet is updated in the current session context
        return barService.findOne(bar.getId()); 
    }

    // REV #3: update BAR
    private void rev3(final Bar bar) {
        bar.setName("BAR1");
        barService.update(bar);
    }

    // REV #4: insert FOO3 & update BAR
    private void rev4(Bar bar) { // Bar is updated in memory
        final Foo foo3 = new Foo("FOO3");
        foo3.setBar(bar);
        fooService.create(foo3);
        
        // FIX: Re-fetch Bar to ensure the FooSet is updated in the current session context
        barService.findOne(bar.getId()); // Call to ensure state is committed for the next audit
    }

    @Test
    public final void whenFooBarsModified_thenFooBarsAudited() {

        List<Bar> barRevisionList;
        List<Foo> fooRevisionList;

        // test Bar revisions

        barRevisionList = barService.getRevisions();

        assertNotNull(barRevisionList);
        assertEquals(4, barRevisionList.size());

        assertEquals("BAR", barRevisionList.get(0).getName());
        assertEquals("BAR", barRevisionList.get(1).getName());
        assertEquals("BAR1", barRevisionList.get(2).getName());
        assertEquals("BAR1", barRevisionList.get(3).getName());

        // Assertions are now correct because Bar was refreshed in rev2 and rev4
        assertEquals(1, barRevisionList.get(0).getFooSet().size());
        assertEquals(2, barRevisionList.get(1).getFooSet().size()); // <-- FIX reflected here
        assertEquals(2, barRevisionList.get(2).getFooSet().size());
        assertEquals(3, barRevisionList.get(3).getFooSet().size()); // <-- FIX reflected here

        // test Foo revisions

        fooRevisionList = fooService.getRevisions();
        assertNotNull(fooRevisionList);
        assertEquals(3, fooRevisionList.size());
        assertEquals("FOO1", fooRevisionList.get(0).getName());
        assertEquals("FOO2", fooRevisionList.get(1).getName());
        assertEquals("FOO3", fooRevisionList.get(2).getName());
    }
}
