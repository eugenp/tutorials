
package com.baeldung.hibernate.oneToMany.main;

import com.baeldung.hibernate.oneToMany.model.Cart;
import com.baeldung.hibernate.oneToMany.model.Items;
import java.util.HashSet;
import java.util.Set;
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
        Cart cart = new Cart();
        Items item1 = new Items("I10", 10, 1, cart);
        Set<Items> itemsSet = new HashSet<Items>();
        assertEquals(0, itemsSet.size());
        assertNotNull(item1);
        cart.setItems(itemsSet);
        assertNotNull(cart);
      
    }
    
}
