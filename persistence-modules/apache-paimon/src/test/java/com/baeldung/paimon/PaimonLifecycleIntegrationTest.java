package com.baeldung.paimon;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.file.Path;
import java.util.List;
import org.apache.paimon.catalog.Catalog;
import org.apache.paimon.catalog.Identifier;
import org.apache.paimon.data.BinaryString;
import org.apache.paimon.data.GenericRow;
import org.apache.paimon.table.Table;
import org.apache.paimon.table.sink.BatchTableWrite;
import org.apache.paimon.table.sink.BatchWriteBuilder;
import org.apache.paimon.table.sink.CommitMessage;
import org.apache.paimon.types.RowKind;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PaimonLifecycleIntegrationTest {
    private static final Logger logger =
        LoggerFactory.getLogger(PaimonLifecycleIntegrationTest.class);

    private Catalog catalog;
    private Identifier tableId;

    private String WAREHOUSE_PATH;

    @BeforeAll
    void setup(@TempDir Path tempDir) {
        WAREHOUSE_PATH = getWarehousePath(tempDir);
        logger.info("Warehouse path set to: {}", WAREHOUSE_PATH);
    }


    @AfterAll
    void cleanup() {
        if (catalog != null) {
            try {
                if (catalog.listTables("metric_db").contains("metrics")) {
                    catalog.dropTable(tableId, true);
                }
                if (catalog.listDatabases().contains("metric_db")) {
                    catalog.dropDatabase("metric_db", true, true);
                }
                logger.info("Cleanup completed successfully");
            } catch (Exception e) {
                logger.error("Error during cleanup", e);
            }
        }
    }

    @Test
    @Order(1)
    void whenCallCreateTable_thenTableCreated() throws Exception {
        logger.info("Warehouse path set to: {}", WAREHOUSE_PATH);
        catalog = PaimonDatabaseManager.createCatalog(WAREHOUSE_PATH);
        assertNotNull(catalog);

        tableId = PaimonDatabaseManager.createTable(catalog);
        assertTrue(catalog.listTables("metric_db").contains("metrics"));
        assertNotNull(tableId);
    }

    @Test
    @Order(2)
    void whenCallInsertRecords_thenRecordsInserted() throws Exception {
        assertTrue(catalog.listTables("metric_db").contains("metrics"));
        PaimonTableDataManager.insert(catalog, tableId, getMetrics());
    }

        @Test
    @Order(3)
    void whenCallReadRecords_thenRecordsRead() throws Exception {
        List<Metric> metrics = PaimonTableDataManager.fetchMetricsBySourceAndDateRange(catalog,
            tableId, "collector", "2026-04-21 18:58:19", "2026-04-21 18:58:30");
        assertFalse(metrics.isEmpty());
        assertTrue(metrics.size() == 4);
    }

    /*
     * @Test
     * 
     * @Order(4)
     */
    void whenCallUpdateRecord_thenRecordUpdated() throws Exception {

        Table table = catalog.getTable(tableId);

        BatchWriteBuilder builder = table.newBatchWriteBuilder();

        BatchTableWrite write = builder.newWrite();

        // update Alice age
        write.write(GenericRow.of(BinaryString.fromString("Alice"), 35));

        List<CommitMessage> messages = write.prepareCommit();

        builder.newCommit().commit(messages);

        assertTrue(messages.size() > 0);
    }

    // ------------------------------------------------
    // 5 Delete Record
    // ------------------------------------------------
    /*
     * @Test
     * 
     * @Order(5)
     */
    void whenCallDeleteRecord_thenRecordDeleted() throws Exception {

        Table table = catalog.getTable(tableId);

        BatchWriteBuilder builder = table.newBatchWriteBuilder();

        BatchTableWrite write = builder.newWrite();

        GenericRow deleteRow = GenericRow.of(BinaryString.fromString("Bob"), 0);

        deleteRow.setRowKind(RowKind.DELETE);

        write.write(deleteRow);

        List<CommitMessage> messages = write.prepareCommit();

        builder.newCommit().commit(messages);

        assertTrue(messages.size() > 0);
    }

    // ------------------------------------------------
    // 6 Read Records
    // ------------------------------------------------


    private List<Metric> getMetrics() {
        return new MetricReader().readMetrics();
    }

    private String getWarehousePath(Path tempDir) {
        return "file:" + tempDir.toString();
    }
}
