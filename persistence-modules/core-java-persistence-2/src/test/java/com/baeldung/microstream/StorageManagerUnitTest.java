package com.baeldung.microstream;

import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class StorageManagerUnitTest {

    @Test
    void givenStorageWithStringAsRoot_whenFetchingRoot_thenExpectedStringIsReturned() {
        EmbeddedStorageManager storageManager = StorageManager.initializeStorageWithStringAsRoot("baeldung-demo-1");
        assertThat(storageManager.root()).isEqualTo("baeldung-demo-1");
        storageManager.shutdown();
    }

    @Test
    void givenStorageWithCustomTypeAsRoot_whenFetchingRoot_thenCustomTypeWithExpectedValuesIsReturned() {
        EmbeddedStorageManager storageManager = StorageManager.initializeStorageWithCustomTypeAsRoot("baeldung-demo-2");
        RootInstance rootInstance = (RootInstance) storageManager.root();
        assertThat(rootInstance.getName()).isEqualTo("baeldung-demo-2");
        assertThat(rootInstance.getBooks()).isEmpty();
        storageManager.shutdown();
    }

    @Test
    void givenStorageWithCustomTypeAsRoot_whenLoadingOrCreateingStorage_thenNonNullRootIsReturned() {
        EmbeddedStorageManager storageManager = StorageManager.loadOrCreateStorage("baeldung-demo-3");
        assertThat(storageManager.root()).isNotNull();
        storageManager.shutdown();
    }

}
