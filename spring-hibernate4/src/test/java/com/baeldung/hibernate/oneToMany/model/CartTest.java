
package com.baeldung.hibernate.oneToMany.model;

import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class CartTest {
    
    public CartTest() {
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
    public void testGetId() {
        System.out.println("getId");
        Cart instance = new Cart();
        long expResult = 0L;
        long result = instance.getId();
        assertEquals(expResult, result);
        fail("The test failed.");
    }

    @Test
    public void testSetId() {
        System.out.println("setId");
        long id = 0L;
        Cart instance = new Cart();
        instance.setId(id);
        fail("The test failed.");
    }

    @Test
    public void testGetTotal() {
        System.out.println("getTotal");
        Cart instance = new Cart();
        double expResult = 0.0;
        double result = instance.getTotal();
        assertEquals(expResult, result, 0.0);
        fail("The test failed.");
    }

    @Test
    public void testSetTotal() {
        System.out.println("setTotal");
        double total = 0.0;
        Cart instance = new Cart();
        instance.setTotal(total);
        fail("The test failed.");
    }

    @Test
    public void testGetName() {
        System.out.println("getName");
        Cart instance = new Cart();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        fail("The test failed");
    }

    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        Cart instance = new Cart();
        instance.setName(name);
        fail("The test failed.");
    }

    @Test
    public void testGetItems() {
        System.out.println("getItems");
        Cart instance = new Cart();
        Set<Items> expResult = null;
        Set<Items> result = instance.getItems();
        assertEquals(expResult, result);
        fail("The test failed.");
    }

    @Test
    public void testSetItems() {
        System.out.println("setItems");
        Set<Items> items = null;
        Cart instance = new Cart();
        instance.setItems(items);
        fail("The test case failed");
    }
    
    
}
