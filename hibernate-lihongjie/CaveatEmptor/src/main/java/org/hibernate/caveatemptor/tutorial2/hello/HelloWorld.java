package org.hibernate.caveatemptor.tutorial2.hello;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.caveatemptor.util.HibernateUtil;

import java.util.Iterator;
import java.util.List;

public class HelloWorld {

    public static void main(String[] args) {

        // ############################################################################

        // First unit of work
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        Message message = new Message("Hello World");
        session.save(message);

        tx.commit();
        session.close();

        // ############################################################################

        // Second unit of work
        Session secondSession = HibernateUtil.getSessionFactory().openSession();
        Transaction secondTransaction = secondSession.beginTransaction();

        List messages =
            secondSession.createQuery("from Message m order by m.text asc").list();

        System.out.println( messages.size() + " message(s) found:" );

        for ( Iterator iter = messages.iterator(); iter.hasNext(); ) {
            Message loadedMsg = (Message) iter.next();
            System.out.println( loadedMsg.getText() );
        }

        secondTransaction.commit();
        secondSession.close();

        // ############################################################################

        // Third unit of work
        Session thirdSession = HibernateUtil.getSessionFactory().openSession();
        Transaction thirdTransaction = thirdSession.beginTransaction();

        // message.getId() holds the identifier value of the first message
        Message loadedMessage = (Message) thirdSession.get( Message.class, message.getId());
        loadedMessage.setText( "Greetings Earthling" );
        loadedMessage.setNextMessage(
            new Message( "Take me to your leader (please)" )
        );

        thirdTransaction.commit();
        thirdSession.close();

        // ############################################################################

        // Final unit of work (just repeat the query)
        // TODO: You can move this query into the thirdSession before the commit, makes more sense!
        Session fourthSession = HibernateUtil.getSessionFactory().openSession();
        Transaction fourthTransaction = fourthSession.beginTransaction();

        messages =
            fourthSession.createQuery("from Message m order by m.text asc").list();

        System.out.println( messages.size() + " message(s) found:" );

        for ( Iterator iter = messages.iterator(); iter.hasNext(); ) {
            Message loadedMsg = (Message) iter.next();
            System.out.println( loadedMsg.getText() );
        }

        fourthTransaction.commit();
        fourthSession.close();


        // Shutting down the application
        HibernateUtil.shutdown();
    }
}
