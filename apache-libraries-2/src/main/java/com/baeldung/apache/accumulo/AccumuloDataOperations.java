package com.baeldung.apache.accumulo;

import org.apache.accumulo.core.client.*;
import org.apache.accumulo.core.data.Key;
import org.apache.accumulo.core.data.Mutation;
import org.apache.accumulo.core.data.Range;
import org.apache.accumulo.core.data.Value;
import org.apache.accumulo.core.security.Authorizations;

import java.io.IOException;
import java.util.Map;

public class AccumuloDataOperations {

    private final AccumuloClient client = createAccumuloClient();

    public AccumuloClient createAccumuloClient() {

        AccumuloClient client = Accumulo.newClient()
                .to("accumuloInstanceName", "localhost:2181")
                .as("username", "password").build();

        return client;
    }

    public void createTable(String tableName) throws TableExistsException, AccumuloException, AccumuloSecurityException {

        client.tableOperations().create(tableName);
        System.out.println("Table " + tableName + " created successfully.");
    }

    public void performBatchWrite(String tableName) throws TableNotFoundException, MutationsRejectedException {

        try (BatchWriter writer = client.createBatchWriter(tableName, new BatchWriterConfig())) {
            Mutation mutation1 = new Mutation("row1");
            mutation1.at()
                    .family("column family 1")
                    .qualifier("column family 1 qualifier 1")
                    .visibility("public").put("value 1");

            Mutation mutation2 = new Mutation("row2");
            mutation2.at()
                    .family("column family 1")
                    .qualifier("column family 1 qualifier 2")
                    .visibility("private").put("value 2");

            writer.addMutation(mutation1);
            writer.addMutation(mutation2);

            System.out.println("Batch write completed successfully.");
        }
    }

    public void scanTableData(String tableName) throws TableNotFoundException {

        try (var scanner = client.createScanner(tableName, new Authorizations("public"))) {
            scanner.setRange(new Range());
            for (Map.Entry<Key, Value> entry : scanner) {
                System.out.println(entry.getKey() + " -> " + entry.getValue());
            }
        }
    }

}