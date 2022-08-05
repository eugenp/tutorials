package com.baeldung.microstream;

import one.microstream.storage.embedded.types.EmbeddedStorage;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;

public class StorageManager {

    public static EmbeddedStorageManager initializeStorageWithStringAsRoot() {
        EmbeddedStorageManager storageManager = EmbeddedStorage.start();
        storageManager.setRoot("baeldung-demo");
        storageManager.storeRoot();
        return storageManager;
    }

    public static EmbeddedStorageManager initializeStorageWithCustomTypeAsRoot() {
        EmbeddedStorageManager storageManager = EmbeddedStorage.start();
        storageManager.setRoot(new RootInstance("baeldung-demo"));
        storageManager.storeRoot();
        return storageManager;
    }

}
