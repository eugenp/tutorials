package com.baeldung.hibernate.criteria.view;

import com.baeldung.hibernate.criteria.model.Employee;
import com.baeldung.hibernate.criteria.util.HibernateUtil;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class EmployeeCriteriaQueries {

  public List<Employee> getAllEmployees() {
    final Session session = HibernateUtil.getHibernateSession();
    final CriteriaBuilder cb = session.getCriteriaBuilder();
    final CriteriaQuery<Employee> cr = cb.createQuery(Employee.class);
    final Root<Employee> root = cr.from(Employee.class);
    cr.select(root);
    Query<Employee> query = session.createQuery(cr);
    List<Employee> results = query.getResultList();
    session.close();
    return results;
  }

  // To get items having salary more than 50000
  public String[] greaterThanCriteria() {
    final Session session = HibernateUtil.getHibernateSession();
    final CriteriaBuilder cb = session.getCriteriaBuilder();
    final CriteriaQuery<Employee> cr = cb.createQuery(Employee.class);
    final Root<Employee> root = cr.from(Employee.class);
    cr.select(root)
      .where(cb.gt(root.get("salary"), 50000));
    Query<Employee> query = session.createQuery(cr);
    final List<Employee> greaterThanEmployeeList = query.getResultList();
    final String employeeWithGreaterSalary[] = new String[greaterThanEmployeeList.size()];
    for (int i = 0; i < greaterThanEmployeeList.size(); i++) {
      employeeWithGreaterSalary[i] = greaterThanEmployeeList.get(i)
        .getName();
    }
    session.close();
    return employeeWithGreaterSalary;
  }

}
