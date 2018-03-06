package org.hibernate.tutorial.tutorial2;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.tutorial.util.HibernateUtil;

import java.util.Iterator;
import java.util.List;

/**
 * Created by lihongjie on 2/5/17.
 */
public class App {
    public static void main(String[] args) {

        // First unit of wok
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Message message = new Message("Hello World");
        Long msgId = (Long) session.save(message);
        tx.commit();
        session.close();
        System.out.println("id: "+msgId);
        // Second unit of work
        Session newSession = HibernateUtil.getSessionFactory().openSession();
        Transaction newTransaction = newSession.beginTransaction();

        List messages = newSession.createQuery("from Message m order by m.text asc").list();

        System.out.println(messages.size() + " messages found: ");
        for (Iterator iterator = messages.iterator(); iterator.hasNext();) {
            Message loadedMsg = (Message) iterator.next();
            System.out.println(loadedMsg.getText());
        }

        newTransaction.commit();
        newSession.close();

        // Third unit of work
        Session thirdSession = HibernateUtil.getSessionFactory().openSession();
        Transaction thirdTransaction = thirdSession.beginTransaction();

        // msgId holds the identifier value of the first message
        message = (Message) thirdSession.get(Message.class, msgId);
        message.setText("Greetings Earthling");
        message.setNextMessage(new Message("Take me to your leader please"));

        thirdTransaction.commit();
        thirdSession.close();
    }
}


/**
 *  Message的实体模型
 */
