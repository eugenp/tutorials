package com.baeldung.hibernate.initialize;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class MyClassWithUnionMethod {

    private final SessionFactory sessionFactory;

    public MyClassWithUnionMethod(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Object[]> executeUnionQuery() {
        Session session = sessionFactory.getCurrentSession();
        @SuppressWarnings("unchecked")
        List<Object[]> results = session.createNativeQuery(
            "SELECT id, name FROM table1 " +
                "UNION " +
                "SELECT id, name FROM table2"
        ).list();
        return results;
    }

    public List<Object[]> executeUnionQuery2() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        // Query for table1
        CriteriaQuery<Object[]> query1 = builder.createQuery(Object[].class);
        Root<Table1> root1 = query1.from(Table1.class);
        query1.multiselect(root1.get("id"), root1.get("name"));

        // Query for table2
        CriteriaQuery<Object[]> query2 = builder.createQuery(Object[].class);
        Root<Table2> root2 = query2.from(Table2.class);
        query2.multiselect(root2.get("id"), root2.get("name"));

        // Union query
        CriteriaQuery<Object[]> unionQuery = builder.createQuery(Object[].class);
        unionQuery.union(query1, query2);

        // Execute the union query
        List<Object[]> results = session.createQuery(unionQuery).getResultList();
        return results;
    }
}