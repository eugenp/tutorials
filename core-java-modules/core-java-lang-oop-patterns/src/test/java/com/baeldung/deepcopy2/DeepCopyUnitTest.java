package com.baeldung.deepcopy2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeepCopyUnitTest {

    @Test
    public void whenModifyingNestedObjectWithCopyConstructor_thenObjectShouldBeTheSame() {

        ConnectionDetails credentials = new ConnectionDetails("sourceUrl", "postgres", "passwordA26");
        DataSource mainDataSource = new DataSource("MainDataSource", credentials);

        // Deep Copy
        DataSource copiedDataSource = new DataSource(mainDataSource);

        credentials.setUsername("postgres_new");
        assertEquals("postgres", copiedDataSource.getConnectionDetails().getUsername());

    }

    @Test
    public void whenModifyingNestedObjectWithClone_thenObjectShouldBeTheSame() {

        ConnectionDetails credentials = new ConnectionDetails("sourceUrl", "postgres", "passwordA26");
        DataSource mainDataSource = new DataSource("MainDataSource", credentials);

        // Deep Copy
        DataSource copiedDataSource = mainDataSource.clone();

        credentials.setUsername("postgres_new");
        assertEquals("postgres", copiedDataSource.getConnectionDetails().getUsername());
    }

}