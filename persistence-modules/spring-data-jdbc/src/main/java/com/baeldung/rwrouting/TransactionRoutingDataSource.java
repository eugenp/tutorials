package com.baeldung.rwrouting;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class TransactionRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        boolean readOnly = TransactionSynchronizationManager
          .isCurrentTransactionReadOnly();

        if (readOnly) {
            return DataSourceType.READ_ONLY;
        }

        return DataSourceType.READ_WRITE;
    }
}
