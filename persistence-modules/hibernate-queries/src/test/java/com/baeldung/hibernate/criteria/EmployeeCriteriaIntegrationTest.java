package com.baeldung.hibernate.criteria;

import static org.junit.Assert.assertArrayEquals;

import com.baeldung.hibernate.criteria.model.Employee;
import com.baeldung.hibernate.criteria.util.HibernateUtil;
import com.baeldung.hibernate.criteria.view.EmployeeCriteriaQueries;
import java.util.List;
import org.hibernate.Session;
import org.junit.Test;

public class EmployeeCriteriaIntegrationTest {

    final private EmployeeCriteriaQueries employeeCriteriaQueries = new EmployeeCriteriaQueries();

    @Test
    public void testGreaterThanCriteriaQuery() {
        final Session session = HibernateUtil.getHibernateSession();
        final List<Employee> expectedGreaterThanList = session.createQuery("From Employee where salary>50000").list();
        final String expectedGreaterThanEmployees[] = new String[expectedGreaterThanList.size()];
        for (int i = 0; i < expectedGreaterThanList.size(); i++) {
            expectedGreaterThanEmployees[i] = expectedGreaterThanList.get(i).getName();
        }
        session.close();
        assertArrayEquals(expectedGreaterThanEmployees, employeeCriteriaQueries.greaterThanCriteria());
    }

    @Test
    public void testGetAllEmployeesQuery() {
        final Session session = HibernateUtil.getHibernateSession();
        final List<Employee> expectedSortCritEmployeeList = session.createQuery("From Employee").list();
        session.close();
        assertArrayEquals(expectedSortCritEmployeeList.toArray(), employeeCriteriaQueries.getAllEmployees().toArray());
    }
}
