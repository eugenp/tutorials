package com.baeldung.hibernate.groupby;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ProductService {

    private final SessionFactory sessionFactory;

    public ProductService() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public List<Object[]> getTotalPricePerCategory() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

            CriteriaQuery<Object[]> query = criteriaBuilder.createQuery(Object[].class);
            Root<Product> root = query.from(Product.class);

            query.multiselect(
                    root.get("category"),
                    criteriaBuilder.sum(root.get("price"))
            ).groupBy(root.get("category"));

            return session.createQuery(query).getResultList();
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}