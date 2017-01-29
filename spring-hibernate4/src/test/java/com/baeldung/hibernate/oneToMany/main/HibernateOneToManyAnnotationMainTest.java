
package com.baeldung.hibernate.oneToMany.main;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class HibernateOneToManyAnnotationMainTest {
    
    public HibernateOneToManyAnnotationMainTest() {
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
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        HibernateOneToManyAnnotationMain.main(args);
        fail("The test failed.");
    }
    
}
