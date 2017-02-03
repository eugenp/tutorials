
package com.baeldung.hibernate.oneToMany.main;


import com.baeldung.hibernate.oneToMany.model.Cart;
import com.baeldung.hibernate.oneToMany.model.Items;
import java.util.HashSet;
import java.util.Set;
import static org.hamcrest.Matchers.hasSize;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.HSQLDialect;
import org.hibernate.service.ServiceRegistry;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class HibernateOneToManyAnnotationMainTest {
    
    private static SessionFactory sessionFactory;

    private Session session;
    
    public HibernateOneToManyAnnotationMainTest() {
    }
    
    
    @BeforeClass
    public static void beforeTests() {
        Configuration configuration = new Configuration().addAnnotatedClass(Cart.class).addAnnotatedClass(Items.class).setProperty("hibernate.dialect", HSQLDialect.class.getName()).setProperty("hibernate.connection.driver_class", org.hsqldb.jdbcDriver.class.getName())
                .setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:test").setProperty("hibernate.connection.username", "sa").setProperty("hibernate.connection.password", "").setProperty("hibernate.hbm2ddl.auto", "update");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }
    
    @Before
    public void setUp() {
        session = sessionFactory.openSession();
        session.beginTransaction();  
        
    }
    
    

    @Test
    public void testAddItemsToCart() {
        Cart cart = new Cart();
        Set <Items> cartItems = new HashSet<>();
        cartItems = cart.getItems();
        Assert.assertNull(cartItems);
        Items item1 = new Items("I10", 10, 1, cart);
        assertNotNull(item1);
        Set<Items> itemsSet = new HashSet<Items>();
        cart.setItems(itemsSet);
        assertNotNull(cart);
        System.out.println("Items added to cart");
      
    }
    
    @Test
    public void testSaveCart(){
       Cart cart = new Cart();
       Set <Items> cartItems = new HashSet<>();
       cartItems = cart.getItems();
       Assert.assertNull(cartItems);
       Items item1 = new Items();
       item1.setItemId("I10");
       item1.setItemTotal(10);
       item1.setQuantity(1);
       item1.setCart(cart);
       assertNotNull(item1);
       Set<Items> itemsSet = new HashSet<Items>();
       itemsSet.add(item1);
       assertNotNull(itemsSet);
       cart.setItems(itemsSet);
       assertNotNull(cart);
       session.persist(cart);
       session.getTransaction().commit();
       session.close();
        
    }
    
    
}
