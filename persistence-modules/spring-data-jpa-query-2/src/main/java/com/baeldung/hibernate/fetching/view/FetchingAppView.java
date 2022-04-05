package com.baeldung.hibernate.fetching.view;

import com.baeldung.hibernate.fetching.model.OrderDetail;
import com.baeldung.hibernate.fetching.model.UserEager;
import com.baeldung.hibernate.fetching.model.UserLazy;
import com.baeldung.hibernate.fetching.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Set;

public class FetchingAppView {

    public FetchingAppView() {

    }

    // lazily loaded
    public Set<OrderDetail> lazyLoaded() {
        final Session sessionLazy = HibernateUtil.getHibernateSession("lazy");
        List<UserLazy> users = sessionLazy.createQuery("From UserLazy").list();
        UserLazy userLazyLoaded = users.get(0);
        // since data is lazyloaded so data won't be initialized
        return (userLazyLoaded.getOrderDetail());
    }

    // eagerly loaded
    public Set<OrderDetail> eagerLoaded() {
        final Session sessionEager = HibernateUtil.getHibernateSession();
        // data should be loaded in the following line
        // also note the queries generated
        List<UserEager> user = sessionEager.createQuery("From UserEager").list();
        UserEager userEagerLoaded = user.get(0);
        return userEagerLoaded.getOrderDetail();
    }

    // creates test data
    // call this method to create the data in the database
    public void createTestData() {

        final Session session = HibernateUtil.getHibernateSession("lazy");
        Transaction tx = session.beginTransaction();
        final UserLazy user1 = new UserLazy();
        final UserLazy user2 = new UserLazy();
        final UserLazy user3 = new UserLazy();

        session.save(user1);
        session.save(user2);
        session.save(user3);

        final OrderDetail order1 = new OrderDetail();
        final OrderDetail order2 = new OrderDetail();
        final OrderDetail order3 = new OrderDetail();
        final OrderDetail order4 = new OrderDetail();
        final OrderDetail order5 = new OrderDetail();

        session.saveOrUpdate(order1);
        session.saveOrUpdate(order2);
        session.saveOrUpdate(order3);
        session.saveOrUpdate(order4);
        session.saveOrUpdate(order5);

        tx.commit();
        session.close();

    }
}
