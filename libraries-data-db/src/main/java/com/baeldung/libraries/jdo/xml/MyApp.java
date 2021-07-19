package com.baeldung.libraries.jdo.xml;

import org.datanucleus.api.jdo.JDOPersistenceManagerFactory;
import org.datanucleus.metadata.PersistenceUnitMetaData;

import javax.jdo.*;
import java.util.List;

public class MyApp {

    private static PersistenceUnitMetaData pumd;
    private static PersistenceManagerFactory pmf;
    private static PersistenceManager pm;

    public static void main(String[] args) {

        // persist product object using dynamic persistence unit
        defineDynamicPersistentUnit();
        Product product = new Product("id1", "Sony Discman", "A standard discman from Sony", 49.99);
        persistObject(product);
        closePersistenceManager();

        // persist AnnotatedPerson object using named pmf
        defineNamedPersistenceManagerFactory("XmlDatastore");
        AnnotadedPerson annotatedPerson = new AnnotadedPerson(654320, "annotated", "person");
        annotatedPerson.getPhoneNumbers().add("999999999");
        annotatedPerson.getPhoneNumbers().add("000000000");
        persistObject(annotatedPerson);
        queryAnnotatedPersonsInXML();
        closePersistenceManager();

        // persist Person object using PMF created by properties file
        definePersistenceManagerFactoryUsingPropertiesFile("META-INF\\datanucleus.properties");
        Person person = new Person(654321, "bealdung", "author");
        person.getPhoneNumbers().add("123456789");
        person.getPhoneNumbers().add("987654321");
        persistObject(person);
        queryPersonsInXML();
        closePersistenceManager();
    }

    public static void defineDynamicPersistentUnit() {

        PersistenceUnitMetaData pumd = new PersistenceUnitMetaData("dynamic-unit", "RESOURCE_LOCAL", null);
        pumd.addProperty("javax.jdo.option.ConnectionURL", "xml:file:myfile_dynamicPMF.xml");
        pumd.addProperty("datanucleus.schema.autoCreateAll", "true");
        pumd.addProperty("datanucleus.xml.indentSize", "4");

        pmf = new JDOPersistenceManagerFactory(pumd, null);
        pm = pmf.getPersistenceManager();
    }

    public static void defineNamedPersistenceManagerFactory(String pmfName) {

        pmf = JDOHelper.getPersistenceManagerFactory("XmlDatastore");
        pm = pmf.getPersistenceManager();
    }

    public static void definePersistenceManagerFactoryUsingPropertiesFile(String filePath) {

        pmf = JDOHelper.getPersistenceManagerFactory(filePath);
        pm = pmf.getPersistenceManager();
    }

    public static void closePersistenceManager() {

        if (pm != null && !pm.isClosed()) {
            pm.close();
        }
    }

    public static void persistObject(Object obj) {

        Transaction tx = pm.currentTransaction();

        try {
            tx.begin();
            pm.makePersistent(obj);
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    public static void queryPersonsInXML() {

        Query<Person> query = pm.newQuery(Person.class);
        List<Person> result = query.executeList();
        System.out.println("name: " + result.get(0).getFirstName());
    }

    public static void queryAnnotatedPersonsInXML() {

        Query<AnnotadedPerson> query = pm.newQuery(AnnotadedPerson.class);
        List<AnnotadedPerson> result = query.executeList();
        System.out.println("name: " + result.get(0).getFirstName());
    }
}
