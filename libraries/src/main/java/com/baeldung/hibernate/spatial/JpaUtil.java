package com.baeldung.hibernate.spatial;

import ch.vorburger.exec.ManagedProcessException;
import ch.vorburger.mariadb4j.DB;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class JpaUtil {

    private static final EntityManagerFactory emFactory;

    static {
        try {
            int port = startDb();
            Map<String, String> properties = getDbProperties(port);
            emFactory = Persistence.createEntityManagerFactory("hibernate-spatial-pu", properties);
        }catch(Throwable ex){
            System.err.println("Cannot create EntityManagerFactory.");
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    @NotNull
    private static Map<String, String> getDbProperties(int port) {
        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.connection.url", "jdbc:mysql://localhost:" + port + "/hibernate-spatial");
        return properties;
    }

    private static int startDb() throws ManagedProcessException {
        DB MYSQL = DB.newEmbeddedDB(0);
        MYSQL.start();
        MYSQL.createDB("hibernate-spatial");
        return MYSQL.getConfiguration().getPort();
    }

    public static EntityManager createEntityManager() {
        return emFactory.createEntityManager();
    }

    public static void close(){
        emFactory.close();
    }
}
