package com.baeldung.hibernate;

public class SchemaMultitenantUtil extends HibernateMultiTenantUtil{
    @Override
    public String tenantUrl(String originalUrl, String tenant) throws UnsupportedTenancyException {
        return String.format(SCHEMA_TOKEN, tenant);

    }
    public static final String SCHEMA_TOKEN = "jdbc:h2:mem:%1$s;DB_CLOSE_DELAY=-1;INIT=CREATE SCHEMA IF NOT EXISTS %1$s\\;SET SCHEMA %1$s";


}
