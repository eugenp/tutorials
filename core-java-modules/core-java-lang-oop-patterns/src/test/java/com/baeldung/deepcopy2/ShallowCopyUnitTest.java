package com.baeldung.deepcopy2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class ShallowCopyUnitTest {


    @Test
    public void whenModifyingNonObject_thenFieldShouldNotBeTheSame() {

        ConnectionDetails credentials = new ConnectionDetails("sourceUrl", "postgres", "passwordA26");
        DataSource mainDataSource = new DataSource("MainDataSource", credentials);

        //Shallow copy
        DataSource copiedDataSource = new DataSource(mainDataSource.getName(), mainDataSource.getConnectionDetails());

        mainDataSource.setName("PrimaryDataSource");
        assertNotEquals(mainDataSource.getName(), copiedDataSource.getName());
    }

    @Test
    public void whenModifyingObject_thenFieldShouldNotBeTheSame() {

        ConnectionDetails credentials = new ConnectionDetails("sourceUrl", "postgres", "passwordA26");
        DataSource mainDataSource = new DataSource("MainDataSource", credentials);

        //Shallow copy
        DataSource copiedDataSource = new DataSource(mainDataSource.getName(), mainDataSource.getConnectionDetails());

        credentials.setUsername("postgres_new");
        assertEquals(mainDataSource.getConnectionDetails().getUsername(), copiedDataSource.getConnectionDetails().getUsername());
    }

}