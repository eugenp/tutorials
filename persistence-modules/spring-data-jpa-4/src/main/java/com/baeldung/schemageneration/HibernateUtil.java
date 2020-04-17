package com.baeldung.schemageneration;

import com.baeldung.schemageneration.model.Account;
import com.baeldung.schemageneration.model.AccountSetting;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class HibernateUtil {

    public static void generateSchema() {
        Map<String, String> settings = new HashMap<>();
        settings.put(Environment.URL, "jdbc:mysql://localhost:3306/baeldung");
        settings.put(Environment.USER, "baeldung");
        settings.put(Environment.PASS, "baeldung");
        settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL57Dialect");

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(settings).build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addAnnotatedClass(Account.class);
        metadataSources.addAnnotatedClass(AccountSetting.class);
        Metadata metadata = metadataSources.buildMetadata();

        SchemaExport schemaExport = new SchemaExport();
        schemaExport.setFormat(true);
        schemaExport.setOutputFile("create.sql");
        schemaExport.createOnly(EnumSet.of(TargetType.SCRIPT), metadata);
    }
}
