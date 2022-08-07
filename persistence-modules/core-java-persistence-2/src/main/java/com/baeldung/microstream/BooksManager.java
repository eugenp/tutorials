package com.baeldung.microstream;

import one.microstream.storage.embedded.types.EmbeddedStorageManager;

import java.util.List;

public class BooksManager {

    public static List<Book> storeBooks(String root, List<Book> booksToStore) {
        EmbeddedStorageManager storageManager = StorageManager.initializeStorageWithCustomTypeAsRoot(root);
        RootInstance rootInstance = (RootInstance) storageManager.root();

        List<Book> books = rootInstance.getBooks();
        books.addAll(booksToStore);

        storageManager.store(books);
        storageManager.shutdown();

        return books;
    }

}
