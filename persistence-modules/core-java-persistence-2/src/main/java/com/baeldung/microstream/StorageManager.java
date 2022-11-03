package com.baeldung.microstream;

import one.microstream.storage.embedded.types.EmbeddedStorage;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;

import java.nio.file.Path;
import java.util.List;

public class StorageManager {

    public static final String STORAGE_ROOT_FOLDER = "storage";

    public static EmbeddedStorageManager initializeStorageWithStringAsRoot(Path directory, String root) {
        EmbeddedStorageManager storageManager = EmbeddedStorage.start(directory);
        storageManager.setRoot(root);
        storageManager.storeRoot();
        return storageManager;
    }

    public static EmbeddedStorageManager initializeStorageWithCustomTypeAsRoot(Path directory, String root) {
        EmbeddedStorageManager storageManager = EmbeddedStorage.start(directory);
        storageManager.setRoot(new RootInstance(root));
        storageManager.storeRoot();
        return storageManager;
    }

    public static EmbeddedStorageManager initializeStorageWithCustomTypeAsRoot(Path directory, String root, List<Book> booksToStore) {
        EmbeddedStorageManager storageManager = EmbeddedStorage.start(directory);
        RootInstance rootInstance = new RootInstance(root);
        storageManager.setRoot(rootInstance);
        storageManager.storeRoot();

        List<Book> books = rootInstance.getBooks();
        books.addAll(booksToStore);
        storageManager.store(books);
        return storageManager;
    }

    public static EmbeddedStorageManager loadOrCreateStorageWithCustomTypeAsRoot(Path directory, String root) {
        EmbeddedStorageManager storageManager = EmbeddedStorage.start(directory);
        if (storageManager.root() == null) {
            RootInstance rootInstance = new RootInstance(root);
            storageManager.setRoot(rootInstance);
            storageManager.storeRoot();
        }
        return storageManager;
    }

    public static EmbeddedStorageManager lazyLoadOrCreateStorageWithCustomTypeAsRoot(Path directory, String root, List<Book> booksToStore) {
        EmbeddedStorageManager storageManager = EmbeddedStorage.start(directory);
        if (storageManager.root() == null) {
            RootInstanceLazy rootInstance = new RootInstanceLazy(root);
            rootInstance.getBooks().addAll(booksToStore);
            storageManager.setRoot(rootInstance);
            storageManager.storeRoot();
        }
        return storageManager;
    }

}
