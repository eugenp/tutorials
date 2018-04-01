package com.lhj.web.test;

import com.lhj.web.hibernate.HibernateSessionFactory;
import com.lhj.web.po.Message;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Created by lihongjie on 12/25/16.
 */
public class HelloWorld {

    public static void main(String[] args) {

        Session session = HibernateSessionFactory.getSession();
        Transaction tx = session.beginTransaction();
        Message message = new Message("Hello World");
        Long msgId = (Long) session.save(message);

        tx.commit();
        session.close();

    }
}
