package com.baeldung.paimon;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.file.Path;
import java.util.List;
import org.apache.paimon.catalog.Catalog;
import org.apache.paimon.catalog.Identifier;
import org.apache.paimon.data.BinaryString;
import org.apache.paimon.data.GenericRow;
import org.apache.paimon.data.InternalRow;
import org.apache.paimon.reader.RecordReader;
import org.apache.paimon.table.Table;
import org.apache.paimon.table.sink.BatchTableWrite;
import org.apache.paimon.table.sink.BatchWriteBuilder;
import org.apache.paimon.table.sink.CommitMessage;
import org.apache.paimon.table.source.ReadBuilder;
import org.apache.paimon.table.source.Split;
import org.apache.paimon.table.source.TableRead;
import org.apache.paimon.types.RowKind;
import org.apache.paimon.utils.CloseableIterator;
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
    private static final Logger logger = LoggerFactory.getLogger(PaimonLifecycleIntegrationTest.class);

    private Catalog catalog;
    private Identifier tableId;

    private String WAREHOUSE_PATH;

    @BeforeAll
    void setup(@TempDir Path tempDir) {
        WAREHOUSE_PATH = getWarehousePath(tempDir);
        logger.info("Warehouse path set to: {}", WAREHOUSE_PATH);
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


    /* @Test
    @Order(4) */
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
/*     @Test
    @Order(5) */
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
    @Test
    @Order(3)
    void whenCallReadRecords_thenRecordsRead() throws Exception {

        Table table = catalog.getTable(tableId);

        ReadBuilder readBuilder = table.newReadBuilder();

        List<Split> splits = readBuilder.newScan().plan().splits();

        TableRead read = readBuilder.newRead();

        RecordReader<InternalRow> reader = read.createReader(splits);

        //List<String> rows = new ArrayList<>();
        CloseableIterator<InternalRow> iterator = reader.toCloseableIterator();
        while(iterator.hasNext()) {
            InternalRow row = iterator.next();
            BinaryString deviceId = row.getString(0);
            logger.info("Read record with deviceId: {}", deviceId);
            //rows.add(row.toString());
        }
    }

    // ------------------------------------------------
    // 7 Cleanup
    // ------------------------------------------------
    @Test
    @Order(4)
    void whenCallCleanup_thenResourcesCleanedUp() throws Exception {

        assertDoesNotThrow(() -> catalog.dropTable(tableId, true), "Table does not exist");
        
        assertDoesNotThrow(() -> catalog.dropDatabase("metric_db", true, true), "Database does not exist");

        logger.info("Cleanup completed successfully");
    }

    private List<Metric> getMetrics() {
        return new MetricReader().readMetrics();
    }

    private String getWarehousePath(Path tempDir) {
        return "file:" + tempDir.toString();
    }
}