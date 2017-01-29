
package com.baeldung.hibernate.oneToMany.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class ItemsTest {
    
    public ItemsTest() {
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
    public void testGetItemId() {
        System.out.println("getItemId");
        Items instance = new Items();
        String expResult = "";
        String result = instance.getItemId();
        assertEquals(expResult, result);
        fail("The test failed.");
    }

    @Test
    public void testSetItemId() {
        System.out.println("setItemId");
        String itemId = "";
        Items instance = new Items();
        instance.setItemId(itemId);
        fail("The test failed.");
    }

    @Test
    public void testGetItemTotal() {
        System.out.println("getItemTotal");
        Items instance = new Items();
        double expResult = 0.0;
        double result = instance.getItemTotal();
        assertEquals(expResult, result, 0.0);
        fail("The test failed.");
    }

    @Test
    public void testSetItemTotal() {
        System.out.println("setItemTotal");
        double itemTotal = 0.0;
        Items instance = new Items();
        instance.setItemTotal(itemTotal);
        fail("The test failed.");
    }

    @Test
    public void testGetQuantity() {
        System.out.println("getQuantity");
        Items instance = new Items();
        int expResult = 0;
        int result = instance.getQuantity();
        assertEquals(expResult, result);
        fail("The test failed.");
    }

    @Test
    public void testSetQuantity() {
        System.out.println("setQuantity");
        int quantity = 0;
        Items instance = new Items();
        instance.setQuantity(quantity);
        fail("The test failed.");
    }

    @Test
    public void testGetCart() {
        System.out.println("getCart");
        Items instance = new Items();
        Cart expResult = null;
        Cart result = instance.getCart();
        assertEquals(expResult, result);
        fail("The test failed.");
    }

    @Test
    public void testSetCart() {
        System.out.println("setCart");
        Cart cart = null;
        Items instance = new Items();
        instance.setCart1(cart);
        fail("The test failed.");
    }

    @Test
    public void testGetId() {
        System.out.println("getId");
        Items instance = new Items();
        long expResult = 0L;
        long result = instance.getId();
        assertEquals(expResult, result);
        fail("The test failed.");
    }

    @Test
    public void testSetId() {
        System.out.println("setId");
        long id = 0L;
        Items instance = new Items();
        instance.setId(id);
        fail("The test failed.");
    }
    
}

    
}
