package com.baeldung.hibernate;

import com.baeldung.hibernate.pojo.Employee;
import com.baeldung.hibernate.pojo.EntityDescription;
import com.baeldung.hibernate.pojo.Phone;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DynamicMappingIntegrationTest {

    private Session session;

    private Transaction transaction;

    @Before
    public void setUp() throws IOException {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();

        session.createNativeQuery("delete from phone").executeUpdate();
        session.createNativeQuery("delete from employee").executeUpdate();

        transaction.commit();
        transaction = session.beginTransaction();
    }

    @After
    public void tearDown() {
        transaction.rollback();
        session.close();
    }

    @Test
    public void givenEntity_whenFieldMappedWithFormula_thenFieldIsCalculated() {
        Employee employee = new Employee(10_000L, 25);
        assertThat(employee.getTaxJavaWay()).isEqualTo(2_500L);

        session.save(employee);
        session.flush();
        session.clear();

        employee = session.get(Employee.class, employee.getId());
        assertThat(employee.getTax()).isEqualTo(2_500L);
    }

    @Test
    public void givenEntityMappedWithWhere_whenDeletedIsTrue_thenEntityNotFetched() {
        Employee employee = new Employee();

        session.save(employee);
        session.clear();

        employee = session.find(Employee.class, employee.getId());
        assertThat(employee).isNotNull();

        employee.setDeleted(true);
        session.flush();

        employee = session.find(Employee.class, employee.getId());
        assertThat(employee).isNotNull();

        session.clear();

        employee = session.find(Employee.class, employee.getId());
        assertThat(employee).isNull();

    }

    @Test
    public void givenCollectionMappedWithWhere_whenDeletedIsTrue_thenEntityNotFetched() {
        Employee employee = new Employee();
        Phone phone1 = new Phone("555-45-67");
        Phone phone2 = new Phone("555-89-01");
        employee.getPhones().add(phone1);
        employee.getPhones().add(phone2);

        session.save(phone1);
        session.save(phone2);
        session.save(employee);
        session.flush();
        session.clear();

        employee = session.find(Employee.class, employee.getId());
        assertThat(employee.getPhones()).hasSize(2);

        employee.getPhones().iterator().next().setDeleted(true);
        session.flush();
        session.clear();

        employee = session.find(Employee.class, employee.getId());
        assertThat(employee.getPhones()).hasSize(1);

        List<Phone> fullPhoneList = session.createQuery("from Phone").getResultList();
        assertThat(fullPhoneList).hasSize(2);

    }

    @Test
    public void givenFilterByIncome_whenIncomeLimitSet_thenFilterIsApplied() throws IOException {
        session.save(new Employee(10_000, 25));
        session.save(new Employee(12_000, 25));
        session.save(new Employee(15_000, 25));

        session.flush();
        session.clear();

        session.enableFilter("incomeLevelFilter")
                .setParameter("incomeLimit", 11_000);

        List<Employee> employees = session.createQuery("from Employee").getResultList();

        assertThat(employees).hasSize(2);

        Employee employee = session.get(Employee.class, 1);
        assertThat(employee.getGrossIncome()).isEqualTo(10_000);

        session.close();

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();

        employees = session.createQuery("from Employee").getResultList();

        assertThat(employees).hasSize(3);

    }

    @Test
    public void givenMappingWithAny_whenDescriptionAddedToEntity_thenDescriptionCanReferAnyEntity() {
        Employee employee = new Employee();
        Phone phone1 = new Phone("555-45-67");
        Phone phone2 = new Phone("555-89-01");
        employee.getPhones().add(phone1);
        employee.getPhones().add(phone2);

        EntityDescription employeeDescription = new EntityDescription("Send to conference next year", employee);
        EntityDescription phone1Description = new EntityDescription("Home phone (do not call after 10PM)", phone1);
        EntityDescription phone2Description = new EntityDescription("Work phone", phone1);

        session.save(phone1);
        session.save(phone2);
        session.save(employee);
        session.save(employeeDescription);
        session.save(phone1Description);
        session.save(phone2Description);
        session.flush();
        session.clear();

        List<EntityDescription> descriptions = session.createQuery("from EntityDescription").getResultList();

        assertThat(Employee.class.isAssignableFrom(descriptions.get(0).getEntity().getClass())).isTrue();
        assertThat(Phone.class.isAssignableFrom(descriptions.get(1).getEntity().getClass())).isTrue();
        assertThat(Phone.class.isAssignableFrom(descriptions.get(2).getEntity().getClass())).isTrue();
    }

}
