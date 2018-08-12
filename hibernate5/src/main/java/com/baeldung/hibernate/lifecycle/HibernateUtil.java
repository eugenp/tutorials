package com.baeldung.hibernate.lifecycle;

import org.h2.tools.RunScript;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.engine.spi.EntityEntry;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.service.ServiceRegistry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static Connection connection;

    public static void init() throws Exception {
        Connection dbConnection = null;
        Class.forName("org.h2.Driver");

        connection = connection = DriverManager.getConnection("jdbc:h2:mem:lifecycledb;DB_CLOSE_DELAY=-1;", "sa", "");
        File initFile = new File("src/test/resources/lifecycle-init.sql");
        try(FileReader reader = new FileReader(initFile);) {
            RunScript.execute(connection, reader);
        }

        ServiceRegistry serviceRegistry = configureServiceRegistry();
        sessionFactory = getSessionFactoryBuilder(serviceRegistry).applyInterceptor(new DirtyDataInspector()).build();
    }

    public static void tearDown() throws Exception {
        sessionFactory.close();
        connection.close();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private static SessionFactoryBuilder getSessionFactoryBuilder(ServiceRegistry serviceRegistry) {
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addAnnotatedClass(FootballPlayer.class);

        Metadata metadata = metadataSources.buildMetadata();
        return metadata.getSessionFactoryBuilder();

    }

    private static ServiceRegistry configureServiceRegistry() throws IOException {
        Properties properties = getProperties();
        return new StandardServiceRegistryBuilder().applySettings(properties).build();
    }

    private static Properties getProperties() throws IOException {
        Properties properties = new Properties();
        URL propertiesURL = Thread.currentThread().getContextClassLoader().getResource("hibernate-lifecycle.properties");
        try (FileInputStream inputStream = new FileInputStream(propertiesURL.getFile())) {
            properties.load(inputStream);
        }
        return properties;
    }

    public static List<EntityEntry> getManagedEntities(Session session) {
        Map.Entry<Object, EntityEntry>[] entries = ((SessionImplementor)session).getPersistenceContext().reentrantSafeEntityEntries();
        return Arrays.asList(entries).stream().map(e -> e.getValue()).collect(Collectors.toList());
    }

    public static Connection getDBConnection() throws Exception {
        return connection;
    }

    public static Transaction startTransaction(Session s) {
        Transaction tx = s.getTransaction();
        tx.begin();
        return tx;
    }

    public static int queryCount(String query) throws Exception {
        try(ResultSet rs = connection.createStatement().executeQuery(query);) {
            rs.next();
            return rs.getInt(1);
        }
    }
}
