package com.baeldung.jdo.xml;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.datanucleus.api.jdo.JDOPersistenceManagerFactory;
import org.datanucleus.metadata.PersistenceUnitMetaData;

public class MyApp_UsingDynamicPersistenceUnit {

    public static void main( String[] args )
    {

        // using Dynmaic persistenceUnit
        PersistenceUnitMetaData pumd = new PersistenceUnitMetaData("dynamic-unit", "RESOURCE_LOCAL", null);
        pumd.addProperty("javax.jdo.option.ConnectionURL", "xml:file:myfile_dynamicPMF.xml");
        pumd.addProperty("datanucleus.schema.autoCreateAll", "true");
        pumd.addProperty("datanucleus.xml.indentSize", "4");

        PersistenceManagerFactory pmf = new JDOPersistenceManagerFactory(pumd, null);

        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        try {
            tx.begin();

            //persist product object
            Product product = new Product("id1","Sony Discman", "A standard discman from Sony", 49.99);
            pm.makePersistent(product);

            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }

    }    
}
