
package com.baeldung.hibernate.oneToMany.config;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class HibernateAnnotationUtilTest {
    
    public HibernateAnnotationUtilTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testGetSessionFactory() {
        System.out.println("getSessionFactory");
        SessionFactory expResult = null;
        SessionFactory result = HibernateAnnotationUtil.getSessionFactory();
        assertEquals(expResult, result);
        fail("The test failed.");
    }
    
}
