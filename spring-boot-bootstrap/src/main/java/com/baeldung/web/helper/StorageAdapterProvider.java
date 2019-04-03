package com.baeldung.web.helper;
/*
 * created by pareshP on 02/04/19
 */

import com.baeldung.ports.AccountOperationsPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StorageAdapterProvider {

    private AccountOperationsPort cacheStorageAdapter;

    private AccountOperationsPort persistentStorageAdapter;

    private boolean persistenceEnabled;

    public StorageAdapterProvider(
            @Autowired AccountOperationsPort cacheStorageAdapter,
            @Autowired AccountOperationsPort persistentStorageAdapter,
            @Value("${persistent.storage.enabled:false}") boolean persistenceEnabled) {
        this.cacheStorageAdapter = cacheStorageAdapter;
        this.persistentStorageAdapter = persistentStorageAdapter;
        this.persistenceEnabled = persistenceEnabled;
    }

    public AccountOperationsPort getStorageAdapter() {
        return persistenceEnabled ? persistentStorageAdapter : cacheStorageAdapter;
    }
}
