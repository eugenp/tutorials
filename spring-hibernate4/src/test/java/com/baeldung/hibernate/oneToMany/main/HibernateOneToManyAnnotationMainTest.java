
package com.baeldung.hibernate.oneToMany.main;


import com.baeldung.hibernate.oneToMany.model.Cart;
import com.baeldung.hibernate.oneToMany.model.Items;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.HSQLDialect;
import org.hibernate.service.ServiceRegistry;
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
        Items item1 = new Items("I10", 10, 1, cart);
        Set<Items> itemsSet = new HashSet<Items>();
        assertEquals(0, itemsSet.size());
        assertNotNull(item1);
        cart.setItems(itemsSet);
        assertNotNull(cart);
        System.out.println("Items added to cart");
      
    }
    
    @Test
    public void testSaveCart(){
       Cart cart = new Cart();
       Items item1 = new Items("I10", 10, 1, cart);
       Set<Items> itemsSet = new HashSet<Items>();
       itemsSet.add(item1);
       cart.setItems(itemsSet);
       session.persist(cart);
       session.getTransaction().commit();
       session.close();
        
    }
    
    
}
