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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class HibernateLifecycleUtil {
    private static SessionFactory sessionFactory;
    private static Connection connection;

    public static void init() throws Exception {
        Properties hbConfigProp = getHibernateProperties();
        Class.forName(hbConfigProp.getProperty("hibernate.connection.driver_class"));
        connection = DriverManager.getConnection(hbConfigProp.getProperty("hibernate.connection.url"), hbConfigProp.getProperty("hibernate.connection.username"), hbConfigProp.getProperty("hibernate.connection.password"));

        try (InputStream h2InitStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("lifecycle-init.sql");
            InputStreamReader h2InitReader = new InputStreamReader(h2InitStream)) {
            RunScript.execute(connection, h2InitReader);
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
        Properties properties = getHibernateProperties();
        return new StandardServiceRegistryBuilder().applySettings(properties).build();
    }

    private static Properties getHibernateProperties() throws IOException {
        Properties properties = new Properties();
        URL propertiesURL = Thread.currentThread().getContextClassLoader().getResource("hibernate-lifecycle.properties");
        try (FileInputStream inputStream = new FileInputStream(propertiesURL.getFile())) {
            properties.load(inputStream);
        }
        return properties;
    }

    public static List<EntityEntry> getManagedEntities(Session session) {
        Map.Entry<Object, EntityEntry>[] entries = ((SessionImplementor) session).getPersistenceContext().reentrantSafeEntityEntries();
        return Arrays.stream(entries).map(e -> e.getValue()).collect(Collectors.toList());
    }

    public static Transaction startTransaction(Session s) {
        Transaction tx = s.getTransaction();
        tx.begin();
        return tx;
    }

    public static int queryCount(String query) throws Exception {
        try (ResultSet rs = connection.createStatement().executeQuery(query)) {
            rs.next();
            return rs.getInt(1);
        }
    }
}
