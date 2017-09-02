package com.baeldung.hibernate;

public class DatabaseMultitenantUtil extends HibernateMultiTenantUtil{
    
    
    @Override
    public String tenantUrl(String originalUrl, String tenant) throws UnsupportedTenancyException {
        return originalUrl.replace(DEFAULT_DB_NAME, tenant);
    }
    
    
    public static final String DEFAULT_DB_NAME = "mydb1";

}
