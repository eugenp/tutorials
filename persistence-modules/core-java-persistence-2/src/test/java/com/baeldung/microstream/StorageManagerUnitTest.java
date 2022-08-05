package com.baeldung.microstream;

import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class StorageManagerUnitTest {

    @Test
    void givenStorageWithStringAsRoot_whenFetchingRoot_thenExpectedStringIsReturned() {
        EmbeddedStorageManager storageManager = StorageManager.initializeStorageWithStringAsRoot();
        assertThat(storageManager.root()).isEqualTo("baeldung-demo");
        storageManager.shutdown();
    }

    @Test
    void givenStorageWithCustomTypeAsRoot_whenFetchingRoot_thenCustomTypeWithExpectedValuesIsReturned() {
        EmbeddedStorageManager storageManager = StorageManager.initializeStorageWithCustomTypeAsRoot();
        RootInstance rootInstance = (RootInstance) storageManager.root();
        assertThat(rootInstance.getName()).isEqualTo("baeldung-demo");
        assertThat(rootInstance.getBooks()).isEmpty();
        storageManager.shutdown();
    }

    @Test
    void givenStorageWithCustomTypeAsRoot_whenStoringAdditionalObjects_thenAdditionalObjectsAreSuccesfullyStored() {
        EmbeddedStorageManager storageManager = StorageManager.initializeStorageWithCustomTypeAsRoot();
        RootInstance rootInstance = (RootInstance) storageManager.root();

        Author author = new Author("Joanne", "Rowling");
        Book bookOne = new Book("Harry Potter and the Philosopher's Stone", author, 1997);
        Book bookTwo = new Book("Harry Potter and the Chamber of Secrets", author, 1998);
        List<Book> books = rootInstance.getBooks();
        books.add(bookOne);
        books.add(bookTwo);
        storageManager.store(rootInstance.getBooks());

        assertThat(rootInstance.getBooks()).hasSize(2);
        assertThat(rootInstance.getBooks().get(0)).isEqualTo(bookOne);
        assertThat(rootInstance.getBooks().get(1)).isEqualTo(bookTwo);

        storageManager.shutdown();
    }

}
