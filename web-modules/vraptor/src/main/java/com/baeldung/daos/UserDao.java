package com.baeldung.daos;

import com.baeldung.models.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.omg.PortableInterceptor.LOCATION_FORWARD;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.logging.Logger;

@RequestScoped
public class UserDao {

    SessionFactory sessionFactory;

    public UserDao() {
        this(null);
    }

    @Inject
    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public Object add(User user) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Object Id = session.save(user);
        session.getTransaction().commit();
        return Id;
    }

    public User findByEmail(String email) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("email", email));
        criteria.setMaxResults(1);
        User u = (User) criteria.uniqueResult();
        session.getTransaction().commit();
        session.close();
        return u;
    }

}
