package com.baeldung.microstream;

import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class StorageManagerUnitTest {

    private static final Author author = new Author("Joanne", "Rowling");
    private static final Book bookOne = new Book("Harry Potter and the Philosopher's Stone", author, 1997);
    private static final Book bookTwo = new Book("Harry Potter and the Chamber of Secrets", author, 1998);

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
    void givenStorageWithCustomTypeAsRoot_whenStoringAdditionalObjects_thenAdditionalObjectsAreSuccessfullyStored() {
        EmbeddedStorageManager storageManager = StorageManager.initializeStorageWithCustomTypeAsRoot("baeldung-demo-3", Arrays.asList(bookOne, bookTwo));
        RootInstance rootInstance = (RootInstance) storageManager.root();

        assertThat(rootInstance.getName()).isEqualTo("baeldung-demo-3");
        assertThat(rootInstance.getBooks()).hasSize(2);
        assertThat(rootInstance.getBooks().get(0)).isEqualTo(bookOne);
        assertThat(rootInstance.getBooks().get(1)).isEqualTo(bookTwo);
    }

    @Test
    void givenStorageWithCustomTypeAsRoot_whenLoadingOrCreatingStorage_thenNonNullRootIsReturned() {
        EmbeddedStorageManager storageManager = StorageManager.loadOrCreateStorage("baeldung-demo-4");
        assertThat(storageManager.root()).isNotNull();
        storageManager.shutdown();
    }

    @Test
    void givenStorageWithCustomTypeAsRoot_whenLazyLoadingAdditionalObjects_thenAdditionalObjectsAreSuccessfullyLoadedOnDemand() {
        EmbeddedStorageManager storageManager = StorageManager.lazyLoadOrCreateStorage("baeldung-demo-5", Arrays.asList(bookOne, bookTwo));
        RootInstanceLazy rootInstance = (RootInstanceLazy) storageManager.root();
        assertThat(rootInstance.getName()).isEqualTo("baeldung-demo-5");
        assertThat(rootInstance.getBooks()).hasSize(2);
        assertThat(rootInstance.getBooks().get(0)).isEqualTo(bookOne);
        assertThat(rootInstance.getBooks().get(1)).isEqualTo(bookTwo);
        storageManager.shutdown();
    }

}
