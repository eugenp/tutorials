package com.baeldung.mybatisvshibernate;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static java.lang.System.out;

public class HibernateUnitTest {
    static SessionFactory sf;

    @BeforeAll
    public static void initialize() throws IOException {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry =
          new StandardServiceRegistryBuilder()
            .build();
        try {
            sf =
              new MetadataSources(registry)
                .addAnnotatedClass(Student.class)
                .buildMetadata()
                .buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }

    }

    @Test
    public void hibernateExample() {
        assert sf != null;
        Student zeesh = new Student("Zeeshan Arif", 246, "zeesh.arif@gmail.com");
        Student bilal = new Student("Bilal Ahmed", 413, "xyz.123@gmail.com");
        sf.inTransaction(session -> {
            session.persist(zeesh);
            session.persist(bilal);
        });
        ;
        Student bilalFetched = sf.fromTransaction(session ->
          session.find(Student.class, bilal.getId()));
        Student zeeshFetched = sf.fromTransaction(session ->
          session.find(Student.class, zeesh.getId()));

        Assertions.assertEquals(zeesh, zeeshFetched);
        Assertions.assertEquals(bilal, bilalFetched);
    }
}
