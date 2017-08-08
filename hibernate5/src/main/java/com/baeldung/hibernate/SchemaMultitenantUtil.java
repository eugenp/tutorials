package com.baeldung.hibernate;

public class SchemaMultitenantUtil extends HibernateMultiTenantUtil{
    @Override
    public String tenantUrl(String originalUrl, String tenant, String tenantStrategy) throws UnsupportedTenancyException {
        return originalUrl ;//+ String.format(SCHEMA_TOKEN, tenant);

    }
//    public static final String SCHEMA_TOKEN = ";INIT=CREATE SCHEMA IF NOT EXISTS %1$s\\;SET SCHEMA %1$s";


}
