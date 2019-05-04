package com.baeldung.singleton;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.ejb.EJB;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CarServiceIntegrationTest {

    public static final Logger LOG = LoggerFactory.getLogger(CarServiceIntegrationTest.class);

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
            .addClasses(CarServiceBean.class, CarServiceSingleton.class, CarServiceEjbSingleton.class, Car.class)
            .addAsResource("META-INF/persistence.xml")
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private CarServiceBean carServiceBean;

    @Inject
    private CarServiceSingleton carServiceSingleton;

    @EJB
    private CarServiceEjbSingleton carServiceEjbSingleton;

    private static Map<String, UUID> idMap = new HashMap<>();

    @Before
    public void setUp() {
        // populate idMap only on first run
        if (idMap.isEmpty()) {
            LOG.info("setUp::carServiceBean: {}", carServiceBean.getId());
            idMap.put("carServiceBeanId", carServiceBean.getId());

            LOG.info("setUp::carServiceSingleton: {}", carServiceSingleton.getId());
            idMap.put("carServiceSingletonId", carServiceSingleton.getId());

            LOG.info("setUp::carServiceEjbSingleton: {}", carServiceEjbSingleton.getId());
            idMap.put("carServiceEjbSingletonId", carServiceEjbSingleton.getId());
        }
    }

    @Test
    public void givenRun1_whenGetId_thenSingletonIdEqual() {
        int testRun = 1;

        assertNotNull(carServiceBean);
        assertNotNull(carServiceSingleton);
        assertNotNull(carServiceEjbSingleton);

        UUID carServiceBeanId = carServiceBean.getId();
        assertEquals(idMap.get("carServiceBeanId"), carServiceBeanId);
        LOG.info("Test run {}::carServiceBeanId: {}", testRun, carServiceBeanId);

        UUID carServiceSingletonId = carServiceSingleton.getId();
        assertEquals(idMap.get("carServiceSingletonId"), carServiceSingletonId);
        LOG.info("Test run {}::carServiceSingletonId: {}", testRun, carServiceSingletonId);

        UUID carServiceEjbSingletonId = carServiceEjbSingleton.getId();
        assertEquals(idMap.get("carServiceEjbSingletonId"), carServiceEjbSingletonId);
        LOG.info("Test run {}::carServiceEjbSingletonId: {}", testRun, carServiceEjbSingletonId);
    }

    @Test
    public void givenRun2_whenGetId_thenSingletonIdEqual() {
        int testRun = 2;

        assertNotNull(carServiceBean);
        assertNotNull(carServiceSingleton);
        assertNotNull(carServiceEjbSingleton);

        UUID carServiceBeanId = carServiceBean.getId();
        assertNotEquals(idMap.get("carServiceBeanId"), carServiceBeanId);
        LOG.info("Test run {}::carServiceBeanId: {}", testRun, carServiceBeanId);

        UUID carServiceSingletonId = carServiceSingleton.getId();
        assertEquals(idMap.get("carServiceSingletonId"), carServiceSingletonId);
        LOG.info("Test run {}::carServiceSingletonId: {}", testRun, carServiceSingletonId);

        UUID carServiceEjbSingletonId = carServiceEjbSingleton.getId();
        assertEquals(idMap.get("carServiceEjbSingletonId"), carServiceEjbSingletonId);
        LOG.info("Test run {}::carServiceEjbSingletonId: {}", testRun, carServiceEjbSingletonId);
    }

    @Test
    public void givenRun3_whenSingleton_thenNoLocking() {
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String model = Double.toString(Math.round(Math.random() * 100));
                    Car car = new Car("Speedster", model);
                    int serviceQueue = carServiceSingleton.service(car);
                    assertTrue(serviceQueue < 10);                     
                }
            }).start();
        }
        return;
    }
    
    @Test
    public void givenRun4_whenEjb_thenLocking() {
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String model = Double.toString(Math.round(Math.random() * 100));
                    Car car = new Car("Speedster", model);
                    int serviceQueue = carServiceEjbSingleton.service(car);
                    assertEquals(0, serviceQueue);
                }
            }).start();
        }
        return;
    }

}