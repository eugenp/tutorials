package com.baeldung.hibernate.multitenancy.schema;

import java.io.IOException;

import org.junit.Test;

import com.baeldung.hibernate.multitenancy.MultitenancyIntegrationTest;
import com.baeldung.hibernate.multitenancy.database.TenantIdNames;

public class SchemaApproachMultitenancyIntegrationTest extends MultitenancyIntegrationTest {

    @Override
    public String getPropertyFile() {
        return "/hibernate-schema-multitenancy.properties";
    }

    @Test
    public void givenSchemaApproach_whenAddingEntries_thenOnlyAddedToConcreteSchema() throws IOException {
        whenCurrentTenantIs(TenantIdNames.MYDB1);
        whenAddCar("Ferrari");
        thenCarFound("Ferrari");
        whenCurrentTenantIs(TenantIdNames.MYDB2);
        thenCarNotFound("Ferrari");
    }

}
