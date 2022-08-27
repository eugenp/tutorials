package com.baeldung.microstream;

import one.microstream.storage.embedded.types.EmbeddedStorage;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;

import java.nio.file.Paths;
import java.util.List;

public class StorageManager {

    public static final String STORAGE_ROOT_FOLDER = "storage";

    public static EmbeddedStorageManager initializeStorageWithStringAsRoot(String root) {
        EmbeddedStorageManager storageManager = EmbeddedStorage.start(Paths.get(STORAGE_ROOT_FOLDER, root));
        storageManager.setRoot(root);
        storageManager.storeRoot();
        return storageManager;
    }

    public static EmbeddedStorageManager initializeStorageWithCustomTypeAsRoot(String root) {
        EmbeddedStorageManager storageManager = EmbeddedStorage.start(Paths.get(STORAGE_ROOT_FOLDER, root));
        storageManager.setRoot(new RootInstance(root));
        storageManager.storeRoot();
        return storageManager;
    }

    public static EmbeddedStorageManager initializeStorageWithCustomTypeAsRoot(String root, List<Book> booksToStore) {
        EmbeddedStorageManager storageManager = EmbeddedStorage.start(Paths.get(STORAGE_ROOT_FOLDER, root));
        RootInstance rootInstance = new RootInstance(root);
        storageManager.setRoot(rootInstance);
        storageManager.storeRoot();

        List<Book> books = rootInstance.getBooks();
        books.addAll(booksToStore);
        storageManager.store(books);
        return storageManager;
    }

    public static EmbeddedStorageManager loadOrCreateStorage(String root) {
        EmbeddedStorageManager storageManager = EmbeddedStorage.start(Paths.get(STORAGE_ROOT_FOLDER, root));
        if (storageManager.root() == null) {
            RootInstance rootInstance = new RootInstance(root);
            storageManager.setRoot(rootInstance);
            storageManager.storeRoot();
        }
        return storageManager;
    }

    public static EmbeddedStorageManager lazyLoadOrCreateStorage(String root, List<Book> booksToStore) {
        EmbeddedStorageManager storageManager = EmbeddedStorage.start(Paths.get(STORAGE_ROOT_FOLDER, root));
        if (storageManager.root() == null) {
            RootInstanceLazy rootInstance = new RootInstanceLazy(root);
            rootInstance.getBooks().addAll(booksToStore);
            storageManager.setRoot(rootInstance);
            storageManager.storeRoot();
        }
        return storageManager;
    }

}
