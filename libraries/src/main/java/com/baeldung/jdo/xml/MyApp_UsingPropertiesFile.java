package com.baeldung.jdo.xml;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

public class MyApp_UsingPropertiesFile {

    public static void main( String[] args )
    {

        //using properties file
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("META-INF\\datanucleus.properties");

        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        try {
            tx.begin();

            // persist person object
            Person person = new Person(654321,"bealdung","author");
            person.getPhoneNumbers().add("123456789");
            person.getPhoneNumbers().add("987654321");
            pm.makePersistent(person);
            

            // retrieve the persisted person
            Query<Person> query = pm.newQuery(Person.class);
            List<Person> result = query.executeList();
            System.out.println("name: "+result.get(0).getFirstName());

            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }

    }    
}
