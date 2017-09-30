package com.baeldung.jdo.xml;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

public class MyApp_UsingNamedPMF {

    public static void main( String[] args )
    {

        //using named PersistenceManagerFactory
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("XmlDatastore");

        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        try {
            tx.begin();

            //persist AnnotadPerson object
            AnnotadedPerson annotatedPerson = new AnnotadedPerson(654320,"annotated","person");
            annotatedPerson.getPhoneNumbers().add("999999999");
            annotatedPerson.getPhoneNumbers().add("000000000");
            pm.makePersistent(annotatedPerson);

            // retrieve the persisted person
            Query<AnnotadedPerson> query = pm.newQuery(AnnotadedPerson.class);
            List<AnnotadedPerson> result = query.executeList();
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
