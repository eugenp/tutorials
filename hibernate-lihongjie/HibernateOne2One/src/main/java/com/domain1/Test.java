package com.domain1;

import com.test.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Test {

    public static void main(String[] args) {
        addUser();
    }

    private static void addUser() {
        Session session = null;
        Transaction tx = null;
        try{
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();
            User newUser = new User();

            Address shippingAddress = new Address();
            newUser.setShippingAddress(shippingAddress);
            session.save(newUser);
            tx.commit();
        }finally {
            if(session!=null)
                session.close();
        }
    }

    private static void addAddress() {

    }
}
