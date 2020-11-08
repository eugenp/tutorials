package com.baeldung.hibernate.transaction;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class PostService {


    private Session session;

    public PostService(Session session) {
        this.session = session;
    }


    public void updatePost(String title, String body, int id) {
        Transaction txn = session.beginTransaction();
        Query updateQuery = session.createQuery("UPDATE Post p SET p.title = ?1, p.body = ?2 WHERE p.id = ?3");
        updateQuery.setParameter(1, title);
        updateQuery.setParameter(2, body);
        updateQuery.setParameter(3, id);
        updateQuery.executeUpdate();
        txn.commit();
    }



}
