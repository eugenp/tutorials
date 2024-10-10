package com.baeldung.arrayscollections.dialects;

import org.hibernate.boot.model.TypeContributions;
import org.hibernate.dialect.Dialect;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.descriptor.sql.internal.*;
import org.hibernate.type.descriptor.sql.spi.DdlTypeRegistry;

import static org.hibernate.type.SqlTypes.*;

public class CustomDialect extends Dialect {

    @Override
    public int getPreferredSqlTypeCodeForArray() {
        return supportsStandardArrays() ? ARRAY : JSON;
    }

    @Override
    protected void registerColumnTypes(TypeContributions typeContributions, ServiceRegistry serviceRegistry) {
        super.registerColumnTypes( typeContributions, serviceRegistry );
        final DdlTypeRegistry ddlTypeRegistry = typeContributions.getTypeConfiguration().getDdlTypeRegistry();

        ddlTypeRegistry.addDescriptor( new DdlTypeImpl( JSON, "jsonb", this ) );
    }
}
