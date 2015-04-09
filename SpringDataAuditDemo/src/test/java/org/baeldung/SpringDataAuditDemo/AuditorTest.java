package org.baeldung.SpringDataAuditDemo;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.baeldung.SpringDataAuditDemo.audit.AuditorAwareImpl;
import org.baeldung.SpringDataAuditDemo.dao.repos.OrderDao;
import org.baeldung.SpringDataAuditDemo.dao.repos.UserDao;
import org.baeldung.SpringDataAuditDemo.model.Order;
import org.baeldung.SpringDataAuditDemo.model.User;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/jpaConfig.xml" })
public class AuditorTest {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AuditorAwareImpl auditorAware;
    @Autowired
    private AuditingEntityListener listener;

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;

    /**
     * Asserts that dependencies are injected by spring IoC container.
     */
    @Before
    public void beforeEachTest() {

        assertThat(ReflectionTestUtils.getField(listener, "handler"), notNullValue());

        assertThat(entityManager, notNullValue());
        assertThat(auditorAware, notNullValue());
        assertThat(orderDao, notNullValue());
        assertThat(userDao, notNullValue());
    }

    /**
     * Programmatically authenticates user.<br><br>
     * Creates new Order and saves it into persistence context. On save spring data resolves auditing annotations.<br><br> 
     * Tests spring data auditing annotations. 
     */
    @Test
    public void testCreateOrder() {
        loadAndAuthenticateUser("Mike");

        Order order = new Order(99.78);

        order = orderDao.save(order);

        assertThat(order.getCreatedDate(), notNullValue());
        assertThat(order.getLastModifiedDate(), notNullValue());

        Object authenticatedUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // assert that authenticated user is assigned to Order.createdBy and Order.lastModifiedBy properties
        assertThat(order.getCreatedBy(), CoreMatchers.equalTo(authenticatedUser));
        assertThat(order.getLastModifiedBy(), CoreMatchers.equalTo(authenticatedUser));

        System.out.println("SAVED ORDER:");
        System.out.println(order);
        System.out.println("createdBy=" + order.getCreatedBy());
        System.out.println("lastModifiedBy=" + order.getLastModifiedBy());
    }

    /**
     *  Programmatically authenticates user.<br><br>
     *  Creates new Order and saves it into persistence context.<br><br>
     *  Programmatically authenticates another user.<br><br>
     *  Updates Order.
     *  Asserts that Order.createdBy holds creator and Order.lastModifiedBy holds updater
     */
    @Test
    public void testModifyOrder() {
        User creator = loadAndAuthenticateUser("Mike");

        Order order = new Order(25.26);

        order = orderDao.save(order);

        // authenticate another user
        User updater = loadAndAuthenticateUser("Jhon");

        order = orderDao.findOne(order.getId());
        order.setPrice(50.52);

        // updates entity if primary key exists in db. Triggers spring data jpa auditing
        order = orderDao.save(order);

        assertThat(order.getCreatedBy(), equalTo(creator));// Mike
        assertThat(order.getLastModifiedBy(), equalTo(updater));// Jhon

        System.out.println("UPDATED ORDER:");
        System.out.println(order);
        System.out.println("createdBy=" + order.getCreatedBy());
        System.out.println("lastModifiedBy=" + order.getLastModifiedBy());
    }

    /**
     * Loads user from database by username argument. Loaded user is authenticated programmatically by spring security.
     * @param username 
     */
    private User loadAndAuthenticateUser(String username) {
        // access user from database. DB is populated on container startup by <jdbc:initialize-database> in jpaConfig.xml
        User userDetails = (User) userDao.loadUserByUsername(username);
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        // programmatically authenticate user
        SecurityContextHolder.getContext().setAuthentication(auth);
        return userDetails;
    }
}
