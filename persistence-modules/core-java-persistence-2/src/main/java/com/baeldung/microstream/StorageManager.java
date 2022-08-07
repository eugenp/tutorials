package com.baeldung.microstream;

import one.microstream.storage.embedded.types.EmbeddedStorage;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;

public class StorageManager {

    public static EmbeddedStorageManager initializeStorageWithStringAsRoot(String root) {
        EmbeddedStorageManager storageManager = EmbeddedStorage.start();
        storageManager.setRoot(root);
        storageManager.storeRoot();
        return storageManager;
    }

    public static EmbeddedStorageManager initializeStorageWithCustomTypeAsRoot(String root) {
        EmbeddedStorageManager storageManager = EmbeddedStorage.start();
        storageManager.setRoot(new RootInstance(root));
        storageManager.storeRoot();
        return storageManager;
    }

    public static EmbeddedStorageManager loadOrCreateStorage(String root) {
        EmbeddedStorageManager storageManager = EmbeddedStorage.start();
        if (storageManager.root() == null) {
            RootInstance rootInstance = new RootInstance(root);
            storageManager.setRoot(rootInstance);
        }
        return storageManager;
    }

}
