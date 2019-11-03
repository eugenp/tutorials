package com.baeldung.hibernate.proxy;

import org.hibernate.*;
import org.hibernate.proxy.HibernateProxy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;

import static org.junit.Assert.fail;

public class HibernateProxyUnitTest {

    private SessionFactory factory;

    private Session session;

    private Company workplace;

    private Employee albert;

    private Employee bob;

    private Employee charlotte;

    @Before
    public void init(){
        try {
            factory = HibernateUtil.getSessionFactory("hibernate.properties");
            session = factory.openSession();
        } catch (HibernateException | IOException e) {
            fail("Failed to initiate Hibernate Session [Exception:" + e.toString() + "]");
        }
    }

    @After
    public void close(){
        if(session != null) {
            session.close();
        }
    }

    @Test
    public void givenAnInexistentEmployeeId_whenUseGetMethod_thenReturnNull() {
        Employee employee = session.get(Employee.class, 14L);
        assertNull(employee);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void givenAnNonExistentEmployeeId_whenUseLoadMethod_thenThrowObjectNotFoundException() {
        Employee employee = session.load(Employee.class, 999L);
        assertNotNull(employee);
        employee.getFirstName();
    }

    @Test
    public void givenAnNonExistentEmployeeId_whenUseLoadMethod_thenReturnAProxy() {
        Employee employee = session.load(Employee.class, 14L);
        assertNotNull(employee);
        assertTrue(employee instanceof HibernateProxy);
    }

    @Test
    public void givenAnEmployeeFromACompany_whenUseLoadMethod_thenCompanyIsAProxy() {
        Transaction tx = session.beginTransaction();

        this.workplace = new Company("Bizco");
        session.save(workplace);

        this.albert = new Employee(workplace, "Albert");
        session.save(albert);

        session.flush();
        session.clear();

        tx.commit();
        this.session = factory.openSession();

        Employee proxyAlbert = session.load(Employee.class, albert.getId());
        assertTrue(proxyAlbert instanceof HibernateProxy);

        // with many-to-one lazy-loading, associations remain proxies
        assertTrue(proxyAlbert.getWorkplace() instanceof HibernateProxy);
    }

    @Test
    public void givenACompanyWithEmployees_whenUseLoadMethod_thenEmployeesAreProxies() {
        Transaction tx = session.beginTransaction();

        this.workplace = new Company("Bizco");
        session.save(workplace);

        this.albert = new Employee(workplace, "Albert");
        session.save(albert);

        session.flush();
        session.clear();

        tx.commit();
        this.session = factory.openSession();

        Company proxyBizco = session.load(Company.class, workplace.getId());
        assertTrue(proxyBizco instanceof HibernateProxy);

        // with one-to-many, associations aren't proxies
        assertFalse(proxyBizco.getEmployees().iterator().next() instanceof HibernateProxy);
    }

    @Test
    public void givenThreeEmployees_whenLoadThemWithBatch_thenReturnAllOfThemWithOneQuery() {
        Transaction tx = session.beginTransaction();

        //We are saving 3 entities with one flush

        this.workplace = new Company("Bizco");
        session.save(workplace);

        this.albert = new Employee(workplace, "Albert");
        session.save(albert);

        this.bob = new Employee(workplace, "Bob");
        session.save(bob);

        this.charlotte = new Employee(workplace, "Charlotte");
        session.save(charlotte);

        session.flush();
        session.clear();

        tx.commit();
        session = factory.openSession();

        Employee proxyAlbert = session.load(Employee.class, this.albert.getId());
        assertNotNull(proxyAlbert);
        assertTrue(proxyAlbert instanceof HibernateProxy);

        Employee proxyBob = session.load(Employee.class, this.bob.getId());
        assertNotNull(proxyBob);
        assertTrue(proxyBob instanceof HibernateProxy);

        Employee proxyCharlotte = session.load(Employee.class, this.charlotte.getId());
        assertNotNull(proxyCharlotte);
        assertTrue(proxyCharlotte instanceof HibernateProxy);

        //Fetching from database 3 entities with one call
        //Select from log: where employee0_.id in (?, ?, ?)
        proxyAlbert.getFirstName();

        assertEquals(proxyAlbert, this.albert);
        assertEquals(proxyBob, this.bob);
        assertEquals(proxyCharlotte, this.charlotte);
    }
}
