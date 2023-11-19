package com.baeldung.hibernate.multitenancy.database;

import org.junit.Test;

import com.baeldung.hibernate.multitenancy.MultitenancyIntegrationTest;

public class DatabaseApproachMultitenancyIntegrationTest extends MultitenancyIntegrationTest {

    @Override
    public String getPropertyFile() {
        return "/hibernate-database-multitenancy.properties";
    }

    @Test
    public void givenDatabaseApproach_whenAddingEntries_thenOnlyAddedToConcreteDatabase() {
        whenCurrentTenantIs(TenantIdNames.MYDB1);
        whenAddCar("myCar");
        thenCarFound("myCar");
        whenCurrentTenantIs(TenantIdNames.MYDB2);
        thenCarNotFound("myCar");
    }

}
