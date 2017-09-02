package com.baeldung.hibernate;

public class SchemaMultitenantUtil extends HibernateMultiTenantUtil{
    @Override
    public String tenantUrl(String originalUrl, String tenant) throws UnsupportedTenancyException {
        return originalUrl ;

    }


}
