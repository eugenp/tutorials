package com.baeldung.microstream;

import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

class StorageManagerUnitTest {

    private static final Author author = new Author("Joanne", "Rowling");
    private static final Book bookOne = new Book("Harry Potter and the Philosopher's Stone", author, 1997);
    private static final Book bookTwo = new Book("Harry Potter and the Chamber of Secrets", author, 1998);

    @Test
    void givenStorageWithStringAsRoot_whenFetchingRoot_thenExpectedStringIsReturned(@TempDir Path tempDir) {
        EmbeddedStorageManager storageManager = StorageManager.initializeStorageWithStringAsRoot(tempDir, "baeldung-demo-1");
        assertThat(storageManager.root()).isEqualTo("baeldung-demo-1");
        storageManager.shutdown();
    }

    @Test
    void givenStorageWithCustomTypeAsRoot_whenFetchingRoot_thenCustomTypeWithExpectedValuesIsReturned(@TempDir Path tempDir) {
        EmbeddedStorageManager storageManager = StorageManager.initializeStorageWithCustomTypeAsRoot(tempDir, "baeldung-demo-2");
        RootInstance rootInstance = (RootInstance) storageManager.root();
        assertThat(rootInstance.getName()).isEqualTo("baeldung-demo-2");
        assertThat(rootInstance.getBooks()).isEmpty();
        storageManager.shutdown();
    }

    @Test
    void givenStorageWithCustomTypeAsRoot_whenStoringAdditionalObjects_thenAdditionalObjectsAreSuccessfullyStored(@TempDir Path tempDir) {
        EmbeddedStorageManager storageManager = StorageManager.initializeStorageWithCustomTypeAsRoot(tempDir, "baeldung-demo-3", Arrays.asList(bookOne, bookTwo));
        RootInstance rootInstance = (RootInstance) storageManager.root();
        assertThat(rootInstance.getName()).isEqualTo("baeldung-demo-3");
        assertThat(rootInstance.getBooks()).hasSize(2);
        assertThat(rootInstance.getBooks().get(0)).isEqualTo(bookOne);
        assertThat(rootInstance.getBooks().get(1)).isEqualTo(bookTwo);
        storageManager.shutdown();
    }

    @Test
    void givenStorageWithCustomTypeAsRoot_whenLoadingOrCreatingStorage_thenNonNullRootIsReturned(@TempDir Path tempDir) {
        EmbeddedStorageManager storageManager = StorageManager.loadOrCreateStorage(tempDir, "baeldung-demo-4");
        assertThat(storageManager.root()).isNotNull();
        storageManager.shutdown();
    }

    @Test
    void givenStorageWithCustomTypeAsRoot_whenLazyLoadingAdditionalObjects_thenAdditionalObjectsAreSuccessfullyLoadedOnDemand(@TempDir Path tempDir) {
        EmbeddedStorageManager storageManager = StorageManager.lazyLoadOrCreateStorage(tempDir, "baeldung-demo-5", Arrays.asList(bookOne, bookTwo));
        RootInstanceLazy rootInstance = (RootInstanceLazy) storageManager.root();
        assertThat(rootInstance.getName()).isEqualTo("baeldung-demo-5");
        assertThat(rootInstance.getBooks()).hasSize(2);
        assertThat(rootInstance.getBooks().get(0)).isEqualTo(bookOne);
        assertThat(rootInstance.getBooks().get(1)).isEqualTo(bookTwo);
        storageManager.shutdown();
    }

    @Test
    void givenStorageWithCustomTypeAsRoot_whenRemovingObjectsFromGraph_thenObjectsAreSuccessfullyRemoved(@TempDir Path tempDir) {
        EmbeddedStorageManager storageManager = StorageManager.lazyLoadOrCreateStorage(tempDir, "baeldung-demo-6", Arrays.asList(bookOne, bookTwo));
        RootInstanceLazy rootInstance = (RootInstanceLazy) storageManager.root();
        List<Book> books = rootInstance.getBooks();
        books.remove(1);
        storageManager.store(books);
        assertThat(rootInstance.getName()).isEqualTo("baeldung-demo-6");
        assertThat(books).hasSize(1);
        storageManager.shutdown();
    }

    @Test
    void givenStorageWithCustomTypeAsRoot_whenFilteringCollectionFromGraph_thenStreamsCanBeUsed(@TempDir Path tempDir) {
        EmbeddedStorageManager storageManager = StorageManager.lazyLoadOrCreateStorage(tempDir, "baeldung-demo-7", Arrays.asList(bookOne, bookTwo));
        RootInstanceLazy rootInstance = (RootInstanceLazy) storageManager.root();
        List<Book> books = rootInstance.getBooks()
          .stream()
          .filter(book -> book.getYear() == 1998)
          .collect(Collectors.toList());
        assertThat(rootInstance.getName()).isEqualTo("baeldung-demo-7");
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getYear()).isEqualTo(1998);
        storageManager.shutdown();
    }

}
